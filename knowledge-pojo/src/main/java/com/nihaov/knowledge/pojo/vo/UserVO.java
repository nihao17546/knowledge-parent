package com.nihaov.knowledge.pojo.vo;

import com.nihaov.knowledge.common.util.DesUtils;
import com.nihaov.knowledge.common.util.SimpleDateUtil;
import com.nihaov.knowledge.pojo.po.UserPO;

/**
 * Created by nihao on 18/4/27.
 */
public class UserVO {
    private String id;
    private String nickname;
    private Integer gender;
    private String headPic;
    private String country;
    private String province;
    private String city;
    private String createdAt;
    private String updatedAt;

    public UserVO() {
    }

    public UserVO(UserPO po) {
        try {
            id = DesUtils.encrypt(po.getId().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        nickname = po.getNickname();
        gender = po.getGender();
        headPic = po.getHeadPic();
        country = po.getCountry();
        province = po.getProvince();
        city = po.getCity();
        if(po.getCreatedAt() != null)
            createdAt = SimpleDateUtil.format(po.getCreatedAt());
        if(po.getUpdatedAt() != null)
            updatedAt = SimpleDateUtil.format(po.getUpdatedAt());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
