package com.tiger.rabbitmq.mybatis.base;

import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 * Create by ljt on 20190306
 */
public interface IBaseMapper<T, E> {

    /**
     * 记录参数名，在mapper.xml文件中会引用到
     */
    String RECORD_PARAM_NAME = "record";

    /**
     * example参数名，在mapper.xml文件中会用到
     */
    String EXAMPLE_PARAM_NAME = "example";

    /**
     * 列名list，在mapper.xml文件中会用到
     */
    String COLUMN_LIST_PARAM_NAME = "columnList";

    /**
     * @param example MyBatis条件组装器
     * @return 记录数
     * @描述：通过example组装条件，查询记录数
     * @返回值：int
     * @异常说明：
     */
    int countByExample(E example);

    /**
     * @param example MyBatis条件组装器
     * @return 受影响行数
     * @描述：通过example组装条件，删除记录
     * @返回值：int
     * @异常说明：
     */
    int deleteByExample(E example);

    /**
     * @param id 主键值
     * @return 受影响行数
     * @描述：通过主键删除记录
     * @返回值：int
     * @异常说明：
     */
    int deleteByPrimaryKey(Serializable id);

    /**
     * @param record 实体记录
     * @return 受影响行数
     * @描述：插入记录
     * @返回值：int @异常说明：
     */
    int insert(T record);

    /**
     * @return 受影响行数
     * @描述：插入记录(遇到属性为null时，则跳过该属性) @param record 实体记录
     * @返回值：int
     * @异常说明：
     */
    int insertSelective(T record);

    /**
     * @param example MyBatis条件组装器
     * @return 实体List
     * @描述：通过example组装条件，查询实体列表
     * @返回值：List<T>
     * @异常说明：
     */
    List<T> selectByExample(E example);

    /**
     * @param id 主键值
     * @return 实体对象
     * @描述：通过主键查询单个实体
     * @返回值：T
     * @异常说明：
     */
    T selectByPrimaryKey(Serializable id);

    /**
     * @param example MyBatis条件组装器
     * @return 受影响行数
     * @描述：通过example组装条件，修改记录(遇到属性为null时，则跳过该属性) @param record 实体记录
     * @返回值：int @异常说明：
     */
    int updateByExampleSelective(@Param(RECORD_PARAM_NAME) T record, @Param(EXAMPLE_PARAM_NAME) E example);

    /**
     * @param record  实体记录
     * @param example MyBatis条件组装器
     * @return 受影响行数
     * @描述：通过example组装条件，修改记录
     * @返回值：int
     * @异常说明：
     */
    int updateByExample(@Param(RECORD_PARAM_NAME) T record, @Param(EXAMPLE_PARAM_NAME) E example);

    /**
     * @param record 实体记录
     * @return 受影响行数
     * @描述：通过主键修改记录(遇到属性为null时，则跳过该属性)
     * @返回值：int @异常说明：
     */
    int updateByPrimaryKeySelective(T record);

    /**
     * @param record 实体记录
     * @return 受影响行数
     * @描述：通过主键修改记录
     * @返回值：int
     * @异常说明：
     */
    int updateByPrimaryKey(T record);
}