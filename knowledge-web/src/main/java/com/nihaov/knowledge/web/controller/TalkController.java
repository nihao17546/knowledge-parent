package com.nihaov.knowledge.web.controller;

import com.nihaov.knowledge.service.ITalkService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by nihao on 18/4/28.
 */
@RestController
@RequestMapping("/talk")
public class TalkController extends BaseController {

    @Resource
    private ITalkService talkService;

    @RequestMapping("/list")
    public String list(@RequestParam(required = false, defaultValue = "0") Integer from,
                       @RequestParam(required = false, defaultValue = "10") Integer rows){
        return ok().pull("list", talkService.getList(from, rows)).json();
    }

    @RequestMapping("/add.needLogin")
    public String add(@RequestParam(required = false, defaultValue = "0") Integer from,
                      @RequestParam(required = false, defaultValue = "10") Integer rows,
                      @RequestParam(required = false, defaultValue = "0") Long parentId,
                      @RequestParam String content,
                      @Value("#{request.getAttribute('userId')}") Integer userId
                      ){
        talkService.addTalk(userId, parentId, content);
        return ok().pull("list", talkService.getList(from, rows)).json();
    }
}
