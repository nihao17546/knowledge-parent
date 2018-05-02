package com.nihaov.knowledge.dao;

import org.apache.ibatis.annotations.Param;

/**
 * Created by nihao on 18/4/28.
 */
public interface ISubDAO {
    int insert(@Param("userId") Integer userId,
               @Param("catalogId") Integer catalogId);
    int delete(@Param("userId") Integer userId,
               @Param("catalogId") Integer catalogId);
    Integer selectByUserIdAndCatalogId(@Param("userId") Integer userId,
                                       @Param("catalogId") Integer catalogId);
}
