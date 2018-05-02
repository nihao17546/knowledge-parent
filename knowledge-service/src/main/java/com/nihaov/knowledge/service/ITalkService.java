package com.nihaov.knowledge.service;

import com.nihaov.knowledge.pojo.vo.TalkVO;

import java.util.List;

/**
 * Created by nihao on 18/4/28.
 */
public interface ITalkService {
    List<TalkVO> getList(Integer from, Integer rows);
    void addTalk(Integer userId, Long parentId, String content);
}
