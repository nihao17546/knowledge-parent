package com.nihaov.knowledge.service;

import com.nihaov.knowledge.pojo.vo.UserVO;

/**
 * Created by nihao on 18/4/26.
 */
public interface IUserService {
    UserVO login(String code, String userInfo, String systemInfo, String ip);
}
