package com.nihaov.knowledge.dao;

import com.nihaov.knowledge.pojo.po.CatalogPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by nihao on 18/3/26.
 */
public interface ICatalogDAO {
    List<CatalogPO> selectLimit(@Param("from") Integer from,
                                @Param("rows") Integer rows);
    List<CatalogPO> selectRecommend();
    CatalogPO selectById(@Param("id") Integer id);
    List<CatalogPO> selectBySub(@Param("userId") Integer userId,
                                @Param("from") Integer from,
                                @Param("rows") Integer rows);
}
