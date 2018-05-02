package com.nihaov.knowledge.service;

import com.nihaov.knowledge.pojo.vo.CatalogInfoVO;
import com.nihaov.knowledge.pojo.vo.CatalogVO;

import java.util.List;

/**
 * Created by nihao on 18/3/26.
 */
public interface ICatalogService {
    List<CatalogVO> getCatalogsLimit(Integer from, Integer rows);
    List<CatalogVO> getRecommend();
    List<CatalogVO> search(String name, Integer from, Integer rows);
    CatalogInfoVO getCatalogInfo(Integer userId, Integer catalogId);
    List<CatalogVO> selectSub(Integer userId, Integer from, Integer rows);
}
