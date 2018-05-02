package com.nihaov.knowledge.web.controller;

import com.nihaov.knowledge.common.util.StaticUtils;
import com.nihaov.knowledge.pojo.po.UserPO;
import com.nihaov.knowledge.pojo.vo.UserVO;
import com.nihaov.knowledge.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by nihao on 18/4/26.
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    @Resource
    private IUserService userService;

    @RequestMapping("/login")
    @ResponseBody
    public String login(HttpServletRequest request,
                        @RequestParam String code,
                        @RequestParam String encryptedData,
                        @RequestParam String iv,
                        @RequestParam String userInfo,
                        @RequestParam String systemInfo){
        String ip = StaticUtils.getIpAddr(request);
        UserVO userVO = userService.login(code, userInfo, systemInfo, ip);
        return ok().pull("info", userVO).json();
    }

}
