/**
 * PageInterceptor.java
 * 2016年11月8日
 */
package com.tiger.rabbitmq.mybatis.query;


import com.tiger.rabbitmq.mybatis.base.PageParameter;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 分页拦截器
 * Create by ljt on 20190306
 */
@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class PageInterceptor implements Interceptor {

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(PageInterceptor.class);

    /**
     * 默认的对象工厂
     */
    private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();

    /**
     * 默认对象包装器工厂
     */
    private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();

    private static final ReflectorFactory DEFAULT_REFLECTOR_FACTORY = new DefaultReflectorFactory();
    ;

    /**
     * 默认DB方言
     */
    private static final String DEFAULT_DIALECT = "mysql";

    /**
     * DB方言
     */
    private static String dialect = DEFAULT_DIALECT;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaStatementHandler = MetaObject.forObject(statementHandler, DEFAULT_OBJECT_FACTORY,
                DEFAULT_OBJECT_WRAPPER_FACTORY, DEFAULT_REFLECTOR_FACTORY);
        // 分离代理对象链(由于目标类可能被多个拦截器拦截，从而形成多次代理，通过下面的两次循环可以分离出最原始的的目标类)
        while (metaStatementHandler.hasGetter("h")) {
            Object object = metaStatementHandler.getValue("h");
            metaStatementHandler = MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY,
                    DEFAULT_REFLECTOR_FACTORY);
        }
        // 分离最后一个代理对象的目标类
        while (metaStatementHandler.hasGetter("target")) {
            Object object = metaStatementHandler.getValue("target");
            metaStatementHandler = MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY,
                    DEFAULT_REFLECTOR_FACTORY);
        }

        MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");

        BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
        Object parameterObject = boundSql.getParameterObject();
        if (parameterObject == null) {
            return invocation.proceed();
        } else {
            PageParameter page = null;

            if (parameterObject instanceof PageParameter) {
                page = (PageParameter) parameterObject;
            } else if (parameterObject instanceof Map) {
                Map<String, Object> pramterMap = (Map<String, Object>) parameterObject;
                Collection<Object> paramters = pramterMap.values();
                if (paramters != null && !paramters.isEmpty()) {
                    for (Object object : paramters) {
                        if (object instanceof PageParameter) {
                            page = (PageParameter) object;
                            break;
                        }
                    }
                }
            }

            if (page == null) { // 如果不带有pageParameter分页参数，则直接跳出
                return invocation.proceed();
            }

            String sql = boundSql.getSql();
            // 重写sql
            String pageSql = buildPageSql(sql, page);
            metaStatementHandler.setValue("delegate.boundSql.sql", pageSql);
            // 采用物理分页后，就不需要mybatis的内存分页了，所以重置下面的两个参数
            metaStatementHandler.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
            metaStatementHandler.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);
            Connection connection = (Connection) invocation.getArgs()[0];
            // 重设分页参数里的总页数等
            fillPageParameter(sql, connection, mappedStatement, boundSql, page);
        }
        // 将执行权交给下一个拦截器
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        // 当目标类是StatementHandler类型时，才包装目标类，否者直接返回目标本身,减少目标被代理的次数
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {
    }

    /**
     * @param sql             sql语句
     * @param connection      连接对象
     * @param mappedStatement MappedStatement
     * @param boundSql        sql封装器
     * @param page            分页参数
     * @描述：从数据库里查询总的记录数并计算总页数，回写进分页参数<code>PageParameter</code>,这样调用者就可用通过 分页参数
     * @返回值：void @异常说明：
     */
    private void fillPageParameter(String sql, Connection connection, MappedStatement mappedStatement,
                                   BoundSql boundSql, PageParameter page) {
        // 记录总记录数
        String countSql = "SELECT COUNT(1) from (" + sql + ") _tmpxledaea";
        PreparedStatement countStmt = null;
        ResultSet rs = null;
        try {
            countStmt = connection.prepareStatement(countSql);
            BoundSql countBS = copyFromBoundSql(mappedStatement, boundSql, countSql);
            setParameters(countStmt, mappedStatement, countBS, boundSql.getParameterObject());
            rs = countStmt.executeQuery();
            int totalCount = 0;
            if (rs.next()) {
                totalCount = rs.getInt(1);
            }
            page.setTotalCount(totalCount);
            // 计算总页数
            int totalPage = totalCount / page.getPageSize() + ((totalCount % page.getPageSize() == 0) ? 0 : 1);
            page.setTotalPage(totalPage);
            LOGGER.debug("==> Total Count : {}", totalCount);
        } catch (SQLException e) {
            LOGGER.error("Ignore this exception", e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    LOGGER.error("Ignore this exception", e);
                }
            }
            if (countStmt != null) {
                try {
                    countStmt.close();
                } catch (SQLException e) {
                    LOGGER.error("Ignore this exception", e);
                }
            }
        }

    }

    /**
     * @param ms       MappedStatement
     * @param boundSql BoundSql
     * @param sql      sql语句
     * @return 复制的BoundSql对象
     * @描述：复制BoundSql对象
     * @返回值：BoundSql
     * @异常说明：
     */
    private BoundSql copyFromBoundSql(MappedStatement ms, BoundSql boundSql, String sql) {
        BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql, boundSql.getParameterMappings(),
                boundSql.getParameterObject());
        for (ParameterMapping mapping : boundSql.getParameterMappings()) {
            String prop = mapping.getProperty();
            if (boundSql.hasAdditionalParameter(prop)) {
                newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
            }
        }
        return newBoundSql;
    }

    /**
     * @param ps              PreparedStatement
     * @param mappedStatement MappedStatement
     * @param boundSql        sql封装器
     * @param parameterObject 参数对象
     * @throws SQLException
     * @描述：设置sql参数值
     * @返回值：void @异常说明：
     */
    private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql,
                               Object parameterObject) throws SQLException {
        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
        parameterHandler.setParameters(ps);
    }

    /**
     * @param sql
     * @param page
     * @return 分页sql
     * @描述：根据数据库类型，生成特定的分页sql
     * @返回值：String
     * @异常说明：
     */
    private String buildPageSql(String sql, PageParameter page) {
        if (page != null) {
            StringBuffer pageSql = null;
            if ("mysql".equals(dialect)) {
                pageSql = buildPageSqlForMysql(sql, page);
                return pageSql.toString();
            } else {
                return sql;
            }
        } else {
            return sql;
        }
    }

    /**
     * @param sql  sql语句
     * @param page 分页参数对象
     * @return MySQL分页语句
     * @描述：构建MySQL的分页语句
     * @返回值：StringBuilder
     * @异常说明：
     */
    private StringBuffer buildPageSqlForMysql(String sql, PageParameter page) {
        StringBuffer pageSql = new StringBuffer();
        if (page.getCurrentPage() < 0) {
            page.setCurrentPage(0);
        }
        String beginrow = String.valueOf((page.getCurrentPage()) * page.getPageSize());
        pageSql.append(sql);
        pageSql.append(" LIMIT " + beginrow + "," + page.getPageSize());
        return pageSql;
    }

    /**
     * @param sql sql语句
     * @return 移除order by后的sql语句
     * @描述：移除sql中的order by
     * @返回值：String
     * @异常说明：
     */
    private String removeOrderBy(String sql) {
        Pattern pattern = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(sql);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "");
        }
        matcher.appendTail(sb);
        // if (LOGGER.isDebugEnabled()) {
        // LOGGER.debug("after remove orderBy:" + sb.toString());
        // }
        return sb.toString();
    }

    /**
     * @param sql SQL语句
     * @return 移除开始部分select的SQL
     * @描述：移除SQL语句开始部分的select
     * @返回值：String
     * @异常说明：
     */
    private String removeSelect(String sql) {
        int begin = sql.toLowerCase().indexOf("from");
        sql = sql.substring(begin);
        // if (LOGGER.isDebugEnabled()) {
        // LOGGER.debug("after remove orderBy:" + sql);
        // }
        return sql;
    }

}
