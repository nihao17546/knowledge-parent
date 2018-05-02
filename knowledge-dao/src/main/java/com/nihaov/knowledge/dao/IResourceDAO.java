package com.nihaov.knowledge.dao;

import com.nihaov.knowledge.pojo.po.ResourcePO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by nihao on 18/3/26.
 */
public interface IResourceDAO {
    List<ResourcePO> selectResourceByCatalogAndType(@Param("catalogId") Integer catalogId,
                                                    @Param("type") Integer type,
                                                    @Param("from") Integer from,
                                                    @Param("rows") Integer rows);
    ResourcePO selectById(@Param("id") Integer id);
    List<ResourcePO> selectAllResourceByCatalog(@Param("catalogId") Integer catalogId);
    @MapKey("id")
    Map<Integer,ResourcePO> selectByIds(@Param("list") List<Integer> list);
}
