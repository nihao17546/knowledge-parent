package com.nihaov.knowledge.service.impl;

import com.nihaov.knowledge.common.constants.RedisKeyConstants;
import com.nihaov.knowledge.common.util.SimpleDateUtil;
import com.nihaov.knowledge.dao.ICatalogDAO;
import com.nihaov.knowledge.dao.ISubDAO;
import com.nihaov.knowledge.pojo.po.CatalogPO;
import com.nihaov.knowledge.pojo.vo.CatalogInfoVO;
import com.nihaov.knowledge.pojo.vo.CatalogVO;
import com.nihaov.knowledge.service.ICatalogService;
import com.nihaov.knowledge.service.IRedisService;
import com.nihaov.knowledge.service.ISolrService;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by nihao on 18/4/14.
 */
@Service
public class CatalogServiceImpl implements ICatalogService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private ICatalogDAO catalogDAO;
    @Resource
    private ISolrService solrCatalogService;
    @Resource
    private IRedisService redisService;
    @Resource
    private ISubDAO subDAO;

    @Override
    public List<CatalogVO> getCatalogsLimit(Integer from, Integer rows) {
        List<CatalogPO> poList = catalogDAO.selectLimit(from, rows);
        List<CatalogVO> voList = poList.stream().map(po -> {
            CatalogVO vo = new CatalogVO(po);
            return vo;
        }).collect(Collectors.toList());
        return voList;
    }

    @Override
    public List<CatalogVO> getRecommend() {
        List<CatalogPO> poList = catalogDAO.selectRecommend();
        List<CatalogVO> voList = poList.stream().map(po -> {
            CatalogVO vo = new CatalogVO(po);
            return vo;
        }).collect(Collectors.toList());
        return voList;
    }

    @Override
    public List<CatalogVO> search(String name, Integer from, Integer rows) {
        SolrQuery query = new SolrQuery();
        query.set("df", "catalog_name");
        query.setQuery(name);
        query.setStart(from);
        query.setRows(rows);

        List<CatalogVO> list = new ArrayList<>();
        QueryResponse response = solrCatalogService.query(query);
        if(response != null){
            SolrDocumentList solrDocumentList = response.getResults();
            for(SolrDocument solrDocument : solrDocumentList){
                list.add(convert(solrDocument));
            }
        }
        return list;
    }

    @Override
    public CatalogInfoVO getCatalogInfo(Integer userId, Integer catalogId) {
        CatalogPO catalogPO = catalogDAO.selectById(catalogId);
        CatalogVO catalogVO = new CatalogVO(catalogPO);
        Map<String,String> redisMap = redisService.mulGet(RedisKeyConstants.get资源阅读次数Key(catalogId, null));
        long pv = 0l;
        for(String key: redisMap.keySet()){
            String value = redisMap.get(key);
            pv += Long.parseLong(value);
        }
        CatalogInfoVO vo = new CatalogInfoVO();
        vo.setCatalog(catalogVO);
        vo.setPv(pv);
        Integer subId = subDAO.selectByUserIdAndCatalogId(userId, catalogId);
        if(subId != null) vo.setSub(true);
        else vo.setSub(false);
        return vo;
    }

    @Override
    public List<CatalogVO> selectSub(Integer userId, Integer from, Integer rows) {
        List<CatalogPO> poList = catalogDAO.selectBySub(userId, from, rows);
        List<CatalogVO> voList = poList.stream().map(po -> {
            CatalogVO vo = new CatalogVO(po);
            return vo;
        }).collect(Collectors.toList());
        return voList;
    }

    private CatalogVO convert(SolrDocument solrDocument){
        CatalogVO vo = new CatalogVO();
        Object id = solrDocument.get("id");
        Object name = solrDocument.get("catalog_name");
        Object coverPath = solrDocument.get("cover_path");
        Object date = solrDocument.get("catalog_date");
        if(id != null) vo.setId(Integer.parseInt(id.toString()));
        if(name != null) vo.setName((String) name);
        if(coverPath != null) vo.setCoverPath((String) coverPath);
        if(date != null) vo.setDate(SimpleDateUtil.format((Date) date));
        return vo;
    }

}
