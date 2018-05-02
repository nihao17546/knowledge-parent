package com.nihaov.knowledge.web.controller;

import com.nihaov.knowledge.common.constants.RedisKeyConstants;
import com.nihaov.knowledge.common.enums.ResourceTypeEnum;
import com.nihaov.knowledge.service.IRedisService;
import com.nihaov.knowledge.service.IResourceService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by nihao on 18/4/14.
 */
@RestController
@RequestMapping("/resource")
public class ResourceController extends BaseController {

    @Resource
    public IResourceService resourceServiceSolr;
    @Resource
    public IResourceService resourceServiceMysql;
    @Resource
    public IRedisService redisService;

    @RequestMapping("/list")
    public String listByCatalog(@RequestParam(required = true) Integer catalogId,
                                @RequestParam(required = true) Integer type,
                                @RequestParam(required = false, defaultValue = "0") Integer from,
                                @RequestParam(required = false, defaultValue = "10") Integer rows){
        return ok().pull("list", resourceServiceSolr.getResourceLimit(catalogId,
                ResourceTypeEnum.getByValue(type), from, rows)).json();
    }

    @RequestMapping("/getById")
    public String getById(Integer catalogId, Integer resourceId){
        redisService.incr(RedisKeyConstants.get资源阅读次数Key(catalogId, resourceId));
        return ok().pull("info", resourceServiceSolr.getById(resourceId)).json();
    }

    @RequestMapping("/search")
    public String search(@RequestParam(required = true) String keywords,
                         @RequestParam(required = false, defaultValue = "0") Integer from,
                         @RequestParam(required = false, defaultValue = "10") Integer rows){
        return ok().pull("list", resourceServiceSolr.search(keywords, from, rows)).json();
    }

    @RequestMapping("/getByCatalog")
    public String getByCatalog(@RequestParam(required = true) Integer catalogId){
        return ok().pull("list", resourceServiceMysql.getAllResourceByCatalogId(catalogId)).json();
    }

}
