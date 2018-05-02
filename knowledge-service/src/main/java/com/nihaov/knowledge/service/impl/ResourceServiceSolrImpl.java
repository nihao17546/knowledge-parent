package com.nihaov.knowledge.service.impl;

import com.nihaov.knowledge.common.enums.ResourceTypeEnum;
import com.nihaov.knowledge.common.util.SimpleDateUtil;
import com.nihaov.knowledge.pojo.vo.ResourceBasicVO;
import com.nihaov.knowledge.pojo.vo.ResourceVO;
import com.nihaov.knowledge.service.IResourceService;
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

/**
 * Created by nihao on 18/4/18.
 */
@Service("resourceServiceSolr")
public class ResourceServiceSolrImpl implements IResourceService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private ISolrService solrService;

    @Override
    public List<ResourceVO> getResourceLimit(Integer catalogId, ResourceTypeEnum type, Integer from, Integer rows) {
        SolrQuery query = new SolrQuery();
        StringBuilder params = new StringBuilder();
        if(catalogId != null){
            params.append("catalog_id:" + catalogId);
        }
        if(type != null){
            params.append(" AND resource_type:" + type.getValue());
        }
        query.setQuery(params.toString());
        if(from != null){
            query.setStart(from);
            if(rows != null){
                query.setRows(rows);
            }
        }

        QueryResponse response = solrService.query(query);
        return getListByResponse(response);
    }

    private ResourceVO convert(SolrDocument solrDocument, boolean containsContent){
        ResourceVO vo = new ResourceVO();
        Object id = solrDocument.get("id");
        Object title = solrDocument.get("resource_title");
        Object coverPath = solrDocument.get("resource_cover_path");
        Object catalogId = solrDocument.get("catalog_id");
        Object type = solrDocument.get("resource_type");
        Object date = solrDocument.get("resource_date");
        if(id != null) vo.setId(Integer.parseInt(id.toString()));
        if(title != null) vo.setTitle((String) title);
        if(coverPath != null) vo.setCoverPath((String) coverPath);
        if(catalogId != null) vo.setCatalogId((Integer) catalogId);
        if(type != null) vo.setType((Integer) type);
        if(date != null) vo.setDate(SimpleDateUtil.format((Date) date));

        if(containsContent){
            Object content = solrDocument.get("resource_content");
            if(content != null) vo.setContent((String) content);
        }
        return vo;
    }

    private ResourceBasicVO convertBasic(SolrDocument solrDocument){
        ResourceBasicVO vo = new ResourceBasicVO();
        Object id = solrDocument.get("id");
        Object title = solrDocument.get("resource_title");
        Object coverPath = solrDocument.get("resource_cover_path");
        Object catalogId = solrDocument.get("catalog_id");
        Object type = solrDocument.get("resource_type");
        Object date = solrDocument.get("resource_date");
        Object catalogTitle = solrDocument.get("catalog_title");
        if(id != null) vo.setId(Integer.parseInt(id.toString()));
        if(title != null) vo.setTitle((String) title);
        if(coverPath != null) vo.setCoverPath((String) coverPath);
        if(catalogId != null) vo.setCatalogId((Integer) catalogId);
        if(type != null) vo.setType((Integer) type);
        if(date != null) vo.setDate(SimpleDateUtil.format((Date) date));
        if(catalogTitle != null) vo.setCatalogTitle((String) catalogTitle);
        return vo;
    }

    private List<ResourceVO> getListByResponse(QueryResponse response){
        if(response == null){
            return new ArrayList<>();
        }
        SolrDocumentList solrDocumentList = response.getResults();
        Long recordCount = solrDocumentList.getNumFound();
        List<ResourceVO> result = new ArrayList<>(recordCount.intValue());
        for (SolrDocument solrDocument : solrDocumentList) {
            result.add(convert(solrDocument, true));
        }
        return result;
    }
    private List<ResourceBasicVO> getBasicListByResponse(QueryResponse response){
        if(response == null){
            return new ArrayList<>();
        }
        SolrDocumentList solrDocumentList = response.getResults();
        Long recordCount = solrDocumentList.getNumFound();
        List<ResourceBasicVO> result = new ArrayList<>(recordCount.intValue());
        for (SolrDocument solrDocument : solrDocumentList) {
            result.add(convertBasic(solrDocument));
        }
        return result;
    }

    @Override
    public ResourceVO getById(Integer id) {
        SolrQuery query = new SolrQuery();
        query.set("df", "id");
        query.setQuery(id.toString());

        QueryResponse response = solrService.query(query);
        if(response != null){
            SolrDocumentList solrDocumentList = response.getResults();
            return convert(solrDocumentList.get(0), true);
        }
        return null;
    }

    @Override
    public List<ResourceBasicVO> search(String keywords, Integer from, Integer rows) {
        SolrQuery query = new SolrQuery();
        query.set("df", "title_multi");
        query.setQuery(keywords);
        query.setStart(from);
        query.setRows(rows);

        QueryResponse response = solrService.query(query);
        return getBasicListByResponse(response);
    }

    @Override
    public List<ResourceVO> getAllResourceByCatalogId(Integer catalogId) {
        return null;
    }
}
