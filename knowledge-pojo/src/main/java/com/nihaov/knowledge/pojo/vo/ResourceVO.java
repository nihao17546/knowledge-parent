package com.nihaov.knowledge.pojo.vo;

import com.nihaov.knowledge.common.util.SimpleDateUtil;
import com.nihaov.knowledge.pojo.po.ResourcePO;

/**
 * Created by nihao on 18/3/26.
 */
public class ResourceVO {
    protected Integer id;
    protected String title;
    protected String coverPath;
    protected Integer catalogId;
    protected Integer type;
    protected String content;
    protected String date;

    private long pv;

    public ResourceVO() {
    }

    public ResourceVO(ResourcePO po) {
        id = po.getId();
        title = po.getTitle();
        coverPath = po.getCoverPath();
        catalogId = po.getCatalogId();
        type = po.getType();
        content = po.getContent();
        if(po.getDate() != null)
            date = SimpleDateUtil.format(po.getDate());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public Integer getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(Integer catalogId) {
        this.catalogId = catalogId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getPv() {
        return pv;
    }

    public void setPv(long pv) {
        this.pv = pv;
    }
}
