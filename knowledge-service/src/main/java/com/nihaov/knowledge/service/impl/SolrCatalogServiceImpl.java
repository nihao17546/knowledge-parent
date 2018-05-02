package com.nihaov.knowledge.service.impl;

import com.nihaov.knowledge.service.ISolrService;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by nihao on 18/4/27.
 */
@Service("solrCatalogService")
public class SolrCatalogServiceImpl implements ISolrService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("#{configProperties['catalogSolrCoreUrl']}")
    private String catalogSolrCoreUrl;

    private SolrServer catalogSolrServer;

    @PostConstruct
    public void init(){
        catalogSolrServer = new HttpSolrServer(catalogSolrCoreUrl);
    }

    @Override
    public QueryResponse query(SolrQuery query) {
        QueryResponse response = null;
        try {
            response = catalogSolrServer.query(query);
        } catch (SolrServerException e) {
            logger.error("solr search ERROR",e);
        }
        return response;
    }
}
