package com.nihaov.knowledge.pojo.vo;

/**
 * Created by nihao on 18/4/27.
 */
public class CatalogInfoVO {
    private CatalogVO catalog;
    private long pv;
    private boolean sub;

    public CatalogVO getCatalog() {
        return catalog;
    }

    public void setCatalog(CatalogVO catalog) {
        this.catalog = catalog;
    }

    public long getPv() {
        return pv;
    }

    public void setPv(long pv) {
        this.pv = pv;
    }

    public boolean isSub() {
        return sub;
    }

    public void setSub(boolean sub) {
        this.sub = sub;
    }
}
