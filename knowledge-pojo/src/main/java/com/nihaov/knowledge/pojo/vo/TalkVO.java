package com.nihaov.knowledge.pojo.vo;

import com.nihaov.knowledge.common.util.SimpleDateUtil;
import com.nihaov.knowledge.pojo.po.TalkPO;

/**
 * Created by nihao on 18/4/28.
 */
public class TalkVO {
    private Long id;
    private Integer userId;
    private String content;
    private Long parentId;
    private String date;
    private UserVO user;

    public TalkVO() {
    }

    public TalkVO(TalkPO po) {
        id = po.getId();
        userId = po.getUserId();
        content = po.getContent();
        parentId = po.getParentId();
        if(po.getDate() != null)
            date = SimpleDateUtil.format(po.getDate());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public UserVO getUser() {
        return user;
    }

    public void setUser(UserVO user) {
        this.user = user;
    }
}
