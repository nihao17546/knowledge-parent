package com.nihaov.knowledge.common.constants;


/**
 * Created by nihao on 18/4/27.
 */
public class RedisKeyConstants {
    private static final String 资源阅读次数 = "knowledge:resource_pv:{catalogId}:{resourceId}";
    public static String get资源阅读次数Key(Integer catalogId, Integer resourceId){
        return 资源阅读次数.replace("{catalogId}", catalogId == null ? "*" : catalogId.toString())
                .replace("{resourceId}", resourceId == null ? "*" : resourceId.toString());
    }
}
