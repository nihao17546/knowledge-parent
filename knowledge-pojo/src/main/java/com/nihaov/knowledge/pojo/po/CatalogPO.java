package com.nihaov.knowledge.pojo.po;

import java.util.Date;

/**
 * Created by nihao on 18/3/26.
 */
public class CatalogPO {
    private Integer id;
    private String name;
    private String coverPath;
    private Date date;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
