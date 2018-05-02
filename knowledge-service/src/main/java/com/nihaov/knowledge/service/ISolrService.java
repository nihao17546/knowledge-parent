package com.nihaov.knowledge.service;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;

/**
 * Created by nihao on 18/4/18.
 */
public interface ISolrService {
    QueryResponse query(SolrQuery query);
}
