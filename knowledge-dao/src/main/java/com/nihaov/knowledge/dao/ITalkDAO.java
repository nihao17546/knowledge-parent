package com.nihaov.knowledge.dao;

import com.nihaov.knowledge.pojo.po.TalkPO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by nihao on 18/4/28.
 */
public interface ITalkDAO {
    int insert(TalkPO talkPO);
    List<TalkPO> selectLimit(@Param("from") Integer from,
                             @Param("rows") Integer rows);
}
