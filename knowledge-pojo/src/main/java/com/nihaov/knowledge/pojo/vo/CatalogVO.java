package com.nihaov.knowledge.pojo.vo;

import com.nihaov.knowledge.common.util.SimpleDateUtil;
import com.nihaov.knowledge.pojo.po.CatalogPO;

/**
 * Created by nihao on 18/3/26.
 */
public class CatalogVO {
    private Integer id;
    private String name;
    private String coverPath;
    private String date;

    public CatalogVO() {
    }

    public CatalogVO(CatalogPO po) {
        id = po.getId();
        name = po.getName();
        coverPath = po.getCoverPath();
        if(po.getDate() != null)
            date = SimpleDateUtil.format(po.getDate());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
