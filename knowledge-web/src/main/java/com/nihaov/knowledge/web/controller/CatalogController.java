package com.nihaov.knowledge.web.controller;

import com.nihaov.knowledge.service.ICatalogService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by nihao on 18/4/14.
 */
@RestController
@RequestMapping("/catalog")
public class CatalogController extends BaseController {

    @Resource
    private ICatalogService catalogService;

    @RequestMapping("/list")
    public String list(@RequestParam(required = false, defaultValue = "0") Integer from,
                       @RequestParam(required = false, defaultValue = "10") Integer rows){
        return ok().pull("list",catalogService.getCatalogsLimit(from, rows)).json();
    }

    @RequestMapping("/search")
    public String search(@RequestParam(required = true) String name,
                         @RequestParam(required = false, defaultValue = "0") Integer from,
                         @RequestParam(required = false, defaultValue = "10") Integer rows){
        return ok().pull("list",catalogService.search(name, from, rows)).json();
    }

    @RequestMapping("/recommendList")
    public String recommendList(){
        return ok().pull("list", catalogService.getRecommend()).json();
    }

    @RequestMapping("/info.needLogin")
    public String info(@Value("#{request.getAttribute('userId')}") Integer userId,
                       Integer catalogId){
        return ok().pull("info", catalogService.getCatalogInfo(userId, catalogId)).json();
    }

    @RequestMapping("/sub.needLogin")
    public String sub(@Value("#{request.getAttribute('userId')}") Integer userId,
                      @RequestParam(required = false, defaultValue = "0") Integer from,
                      @RequestParam(required = false, defaultValue = "10") Integer rows){
        return ok().pull("list", catalogService.selectSub(userId, from, rows)).json();
    }
}
