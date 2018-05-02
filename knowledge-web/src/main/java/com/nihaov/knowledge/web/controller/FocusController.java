package com.nihaov.knowledge.web.controller;

import com.nihaov.knowledge.service.IFocusService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by nihao on 18/4/26.
 */
@RestController
@RequestMapping("/focus")
public class FocusController extends BaseController {

    @Resource
    private IFocusService focusService;

    @RequestMapping("/list")
    public String list(){
        return ok().pull("list", focusService.getFocuses()).json();
    }
}
