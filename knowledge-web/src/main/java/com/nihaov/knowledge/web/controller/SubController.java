package com.nihaov.knowledge.web.controller;

import com.nihaov.knowledge.service.ISubService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by nihao on 18/4/28.
 */
@RestController
@RequestMapping("/sub")
public class SubController extends BaseController {

    @Resource
    private ISubService subService;

    @RequestMapping("/add.needLogin")
    public String add(@Value("#{request.getAttribute('userId')}") Integer userId,
                      Integer catalogId){
        subService.add(userId, catalogId);
        return ok().json();
    }

    @RequestMapping("/remove.needLogin")
    public String remove(@Value("#{request.getAttribute('userId')}") Integer userId,
                      Integer catalogId){
        subService.remove(userId, catalogId);
        return ok().json();
    }
}
