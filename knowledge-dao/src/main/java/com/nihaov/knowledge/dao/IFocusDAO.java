package com.nihaov.knowledge.dao;

import com.nihaov.knowledge.pojo.po.FocusPO;

import java.util.List;

/**
 * Created by nihao on 18/4/26.
 */
public interface IFocusDAO {
    List<FocusPO> selectOrderByPosition();
}
