package com.xu.calligraphy.boot.dal.mapper;

/**
 * @author xu
 * @date 2020/1/1 13:38
 */
public interface BaseMapper<T> {
    int deleteByPrimaryKey(Long id);

    int insert(T record);

    int insertSelective(T record);

    T selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(T record);

    int updateByPrimaryKeyWithBLOBs(T record);

    int updateByPrimaryKey(T record);
}
