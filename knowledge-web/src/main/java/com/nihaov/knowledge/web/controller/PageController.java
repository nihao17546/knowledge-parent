package com.nihaov.knowledge.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by nihao on 18/4/17.
 */
@Controller
@RequestMapping("/page")
public class PageController extends BaseController {
    @RequestMapping("/index")
    public String index(){
        return "index";
    }
}
