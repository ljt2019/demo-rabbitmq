package com.tiger.rabbitmq.mybatis.base;

import java.io.Serializable;
import java.util.List;

/**
 * Create by ljt on 20190306
 */
public interface IBaseService<T, E> {

    /**
     * @param example MyBatis条件组装器
     * @return 记录数
     * @描述：通过example组装条件，查询记录数
     * @返回值：int
     * @异常说明：
     */
    public int countByExample(E example);

    /**
     * @param example MyBatis条件组装器
     * @return 受影响行数
     * @描述：通过example组装条件，删除记录
     * @返回值：int
     * @异常说明：
     */
    public int deleteByExample(E example);

    /**
     * @param id 主键值
     * @return 受影响行数
     * @描述：通过主键删除记录
     * @返回值：int
     * @异常说明：
     */
    public int deleteByPrimaryKey(Serializable id);

    /**
     * @param record 实体记录
     * @return 受影响行数
     * @描述：插入记录
     * @返回值：int
     * @异常说明：
     */
    public int insert(T record);

    /**
     * @param record 实体记录
     * @return 受影响行数
     * @描述：插入记录(遇到属性为null时，则跳过该属性)
     * @返回值：int
     * @异常说明：
     */
    public int insertSelective(T record);

    /**
     * @param example MyBatis条件组装器
     * @return 实体List
     * @描述：通过example组装条件，查询实体列表
     * @返回值：List<T>
     * @异常说明：
     */
    public List<T> selectByExample(E example);

    /**
     * @param id 主键值
     * @return 实体对象
     * @描述：通过主键查询单个实体
     * @返回值：T
     * @异常说明：
     */
    public T selectByPrimaryKey(Serializable id);

    /**
     * @param record  实体记录
     * @param example MyBatis条件组装器
     * @return 受影响行数
     * @描述：通过example组装条件，修改记录(遇到属性为null时，则跳过该属性)
     * @返回值：int
     * @异常说明：
     */
    public int updateByExampleSelective(T record, E example);

    /**
     * @param record  实体记录
     * @param example MyBatis条件组装器
     * @return 受影响行数
     * @描述：通过example组装条件，修改记录
     * @返回值：int
     * @异常说明：
     */
    public int updateByExample(T record, E example);

    /**
     * @param record 实体记录
     * @return 受影响行数
     * @描述：通过主键修改记录(遇到属性为null时，则跳过该属性)
     * @返回值：int
     * @异常说明：
     */
    public int updateByPrimaryKeySelective(T record);

    /**
     * @param record 实体记录
     * @return 受影响行数
     * @描述：通过主键修改记录
     * @返回值：int
     * @异常说明：
     */
    public int updateByPrimaryKey(T record);

    /**
     * @param example MyBatis条件组装器
     * @return 单个实体
     * @throws DaoException 如果查出多条记录则抛出这个异常
     * @描述：通过example组装条件，查询一条实体记录
     * @返回值：T
     * @异常说明：
     */
    public T selectOneByExample(E example);

}