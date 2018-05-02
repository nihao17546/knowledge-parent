package com.nihaov.knowledge.pojo.vo;

import com.nihaov.knowledge.common.util.SimpleDateUtil;
import com.nihaov.knowledge.pojo.po.FocusPO;


/**
 * Created by nihao on 18/4/26.
 */
public class FocusVO {
    private Integer id;
    private Integer resourceId;
    private String coverPath;
    private Integer position;
    private String date;

    private ResourceVO resource;

    public FocusVO() {
    }

    public FocusVO(FocusPO po) {
        this.id = po.getId();
        this.resourceId = po.getResourceId();
        this.coverPath = po.getCoverPath();
        this.position = po.getPosition();
        if(po.getDate() != null){
            date = SimpleDateUtil.format(po.getDate());
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ResourceVO getResource() {
        return resource;
    }

    public void setResource(ResourceVO resource) {
        this.resource = resource;
    }
}
