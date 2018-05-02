package com.nihaov.knowledge.service;

import java.util.List;
import java.util.Map;

/**
 * Created by nihao on 18/4/27.
 */
public interface IRedisService {
    String get(String key);
    List<String> keys(String pattern);
    Map<String,String> mulGet(String pattern);
    Long incr(String key);
}
