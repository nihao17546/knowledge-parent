package com.nihaov.knowledge.service;

import com.nihaov.knowledge.common.enums.ResourceTypeEnum;
import com.nihaov.knowledge.pojo.vo.ResourceBasicVO;
import com.nihaov.knowledge.pojo.vo.ResourceVO;

import java.util.List;

/**
 * Created by nihao on 18/4/14.
 */
public interface IResourceService {
    List<ResourceVO> getResourceLimit(Integer catalogId, ResourceTypeEnum type,
                                      Integer from, Integer rows);
    ResourceVO getById(Integer id);
    List<ResourceBasicVO> search(String keywords, Integer from, Integer rows);

    List<ResourceVO> getAllResourceByCatalogId(Integer catalogId);
}
