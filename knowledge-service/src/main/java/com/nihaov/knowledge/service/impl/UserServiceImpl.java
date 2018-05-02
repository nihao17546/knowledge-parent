package com.nihaov.knowledge.service.impl;

import com.alibaba.fastjson.JSON;
import com.nihaov.knowledge.common.util.StaticUtils;
import com.nihaov.knowledge.common.util.UTF8Utils;
import com.nihaov.knowledge.dao.ILoginDAO;
import com.nihaov.knowledge.dao.IUserDAO;
import com.nihaov.knowledge.pojo.po.LoginPO;
import com.nihaov.knowledge.pojo.po.UserPO;
import com.nihaov.knowledge.pojo.vo.UserVO;
import com.nihaov.knowledge.pojo.wx.WxSystemInfo;
import com.nihaov.knowledge.pojo.wx.WxUserInfo;
import com.nihaov.knowledge.service.IUserService;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by nihao on 18/4/26.
 */
@Service
public class UserServiceImpl implements IUserService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private IUserDAO userDAO;
    @Resource
    private ILoginDAO loginDAO;

    @Value("#{configProperties['wxSnsUrl']}")
    private String wxSnsUrl;
    @Value("#{configProperties['appId']}")
    private String appId;
    @Value("#{configProperties['wxSecret']}")
    private String wxSecret;

    private UserPO getUserPO(String code, String userInfo){
        HttpPost httpPost = new HttpPost(wxSnsUrl);
        StringBody appid = new StringBody(appId, ContentType.create("text/plain", Consts.UTF_8));
        StringBody secret = new StringBody(wxSecret, ContentType.create("text/plain", Consts.UTF_8));
        StringBody js_code = new StringBody(code, ContentType.create("text/plain", Consts.UTF_8));
        StringBody grant_type = new StringBody("authorization_code", ContentType.create("text/plain", Consts.UTF_8));
        HttpEntity reqEntity = MultipartEntityBuilder.create()
                .addPart("appid",appid)
                .addPart("secret",secret)
                .addPart("js_code",js_code)
                .addPart("grant_type",grant_type)
                .build();
        httpPost.setEntity(reqEntity);
        try(CloseableHttpClient httpClient = HttpClients.createDefault();
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost)){
            HttpEntity entity = httpResponse.getEntity();
            String resultStr = EntityUtils.toString(entity, "utf-8");
            Map<String,String> result = JSON.parseObject(resultStr,Map.class);
            if(result.containsKey("openid")){
                String openid = result.get("openid");
                WxUserInfo wxUserInfo = JSON.parseObject(userInfo, WxUserInfo.class);
                if(wxUserInfo.getNickName() != null){
                    if(UTF8Utils.contains4BytesChar2(wxUserInfo.getNickName())){
                        wxUserInfo.setNickName(new String(UTF8Utils.remove4BytesUTF8Char(wxUserInfo.getNickName()),"UTF-8"));
                    }
                }
                UserPO userPO = new UserPO();
                userPO.setNickname(wxUserInfo.getNickName());
                userPO.setGender(wxUserInfo.getGender());
                userPO.setHeadPic(wxUserInfo.getAvatarUrl());
                userPO.setOpenId(openid);
                userPO.setCountry(wxUserInfo.getCountry());
                userPO.setProvince(wxUserInfo.getProvince());
                userPO.setCity(wxUserInfo.getCity());
                return userPO;
            }
            else if(result.containsKey("errmsg")){
                throw new RuntimeException(result.get("errmsg"));
            }
            else{
                throw new RuntimeException("授权登录失败,微信响应数据 --> " + resultStr);
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Transactional
    @Override
    public UserVO login(String code, String userInfo, String systemInfo, String ip) {
        UserPO userPO = getUserPO(code, userInfo);
        UserPO checkUserPO = userDAO.selectByOpenId(userPO.getOpenId());
        if(checkUserPO == null){
            userDAO.insert(userPO);
        }
        else{
            userPO.setId(checkUserPO.getId());
            if(!StaticUtils.eq(userPO.getNickname(), checkUserPO.getNickname())
                    || !StaticUtils.eq(userPO.getHeadPic(), checkUserPO.getHeadPic())
                    || !StaticUtils.eq(userPO.getCountry(), checkUserPO.getCountry())
                    || !StaticUtils.eq(userPO.getProvince(), checkUserPO.getProvince())
                    || !StaticUtils.eq(userPO.getCity(), checkUserPO.getCity())
                    || userPO.getGender() != checkUserPO.getGender()){
                userDAO.update(userPO);
            }
        }
        WxSystemInfo wxSystemInfo = JSON.parseObject(systemInfo, WxSystemInfo.class);
        LoginPO loginPO = new LoginPO();
        loginPO.setUserId(userPO.getId());
        loginPO.setBrand(wxSystemInfo.getBrand());
        loginPO.setModel(wxSystemInfo.getModel());
        loginPO.setSystem(wxSystemInfo.getSystem());
        loginPO.setVersion(wxSystemInfo.getVersion());
        loginPO.setPlatform(wxSystemInfo.getPlatform());
        loginPO.setIp(ip);
        loginDAO.insert(loginPO);
        return new UserVO(userPO);
    }
}
