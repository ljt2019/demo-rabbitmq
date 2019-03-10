package com.tiger.rabbitmq.mybatis.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * Create by ljt on 20190306
 */
public abstract class BaseServiceImpl<T, E> implements IBaseService<T, E> {

    /**
     * 日志对象
     */
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * @return
     * @描述：获取与实体对应的mapper
     * @返回值：IBaseMapper<T,E>
     * @异常说明：
     */
    public abstract IBaseMapper<T, E> getBaseMapper();

    /**
     * @param example MyBatis条件组装器
     * @return 记录数
     * @描述：通过example组装条件，查询记录数
     * @返回值：int
     * @异常说明：
     */
    @Override
    public int countByExample(E example) {
        return getBaseMapper().countByExample(example);
    }

    /**
     * @param example MyBatis条件组装器
     * @return 受影响行数
     * @描述：通过example组装条件，删除记录
     * @返回值：int
     * @异常说明：
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByExample(E example) {
        int count = 0;
        try {
            count = getBaseMapper().deleteByExample(example);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DaoException(e.getMessage(), e);
        }
        return count;
    }

    /**
     * @param id 主键值
     * @return 受影响行数
     * @描述：通过主键删除记录
     * @返回值：int
     * @异常说明：
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByPrimaryKey(Serializable id) {
        int count = 0;
        try {
            count = getBaseMapper().deleteByPrimaryKey(id);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DaoException(e.getMessage(), e);
        }
        return count;
    }

    /**
     * @param record 实体记录
     * @return 受影响行数
     * @描述：插入记录
     * @返回值：int
     * @异常说明：
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(T record) {
        int count = 0;
        try {
            count = getBaseMapper().insert(record);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DaoException(e.getMessage(), e);
        }
        return count;
    }

    /**
     * @param record 实体记录
     * @return 受影响行数
     * @描述：插入记录(遇到属性为null时，则跳过该属性)
     * @返回值：int
     * @异常说明：
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertSelective(T record) {
        int count = 0;
        try {
            count = getBaseMapper().insertSelective(record);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DaoException(e.getMessage(), e);
        }
        return count;
    }

    /**
     * @param example MyBatis条件组装器
     * @return 实体List
     * @描述：通过example组装条件，查询实体列表
     * @返回值：List<T>
     * @异常说明：
     */
    @Override
    public List<T> selectByExample(E example) {
        return getBaseMapper().selectByExample(example);
    }

    /**
     * @param id 主键值
     * @return 实体对象
     * @描述：通过主键查询单个实体
     * @返回值：T
     * @异常说明：
     */
    @Override
    public T selectByPrimaryKey(Serializable id) {
        return getBaseMapper().selectByPrimaryKey(id);
    }

    /**
     * @param record  实体记录
     * @param example MyBatis条件组装器
     * @return 受影响行数
     * @描述：通过example组装条件，修改记录(遇到属性为null时，则跳过该属性)
     * @返回值：int
     * @异常说明：
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateByExampleSelective(T record, E example) {
        int count = 0;
        try {
            count = getBaseMapper().updateByExampleSelective(record, example);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DaoException(e.getMessage(), e);
        }
        return count;
    }

    /**
     * @param record  实体记录
     * @param example MyBatis条件组装器
     * @return 受影响行数
     * @描述：通过example组装条件，修改记录
     * @返回值：int
     * @异常说明：
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateByExample(T record, E example) {
        int count = 0;
        try {
            count = getBaseMapper().updateByExample(record, example);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DaoException(e.getMessage(), e);
        }
        return count;
    }

    /**
     * @param record 实体记录
     * @return 受影响行数
     * @描述：通过主键修改记录(遇到属性为null时，则跳过该属性)
     * @返回值：int
     * @异常说明：
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateByPrimaryKeySelective(T record) {
        int count = 0;
        try {
            count = getBaseMapper().updateByPrimaryKeySelective(record);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DaoException(e.getMessage(), e);
        }
        return count;
    }

    /**
     * @param record 实体记录
     * @return 受影响行数
     * @描述：通过实体修改记录
     * @返回值：int
     * @异常说明：
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateByPrimaryKey(T record) {
        int count = 0;
        try {
            count = getBaseMapper().updateByPrimaryKey(record);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new DaoException(e.getMessage(), e);
        }
        return count;
    }

    @Override
    public T selectOneByExample(E example) throws DaoException {
        List<T> list = getBaseMapper().selectByExample(example);
        if (list == null || list.size() == 0) {
            return null;
        } else if (list.size() > 1) {
            throw new DaoException("返回的记录多于1条");
        }
        return list.get(0);
    }

}