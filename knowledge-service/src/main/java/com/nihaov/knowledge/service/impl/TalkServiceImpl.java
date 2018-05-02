package com.nihaov.knowledge.service.impl;

import com.nihaov.knowledge.dao.ITalkDAO;
import com.nihaov.knowledge.dao.IUserDAO;
import com.nihaov.knowledge.pojo.po.TalkPO;
import com.nihaov.knowledge.pojo.po.UserPO;
import com.nihaov.knowledge.pojo.vo.TalkVO;
import com.nihaov.knowledge.pojo.vo.UserVO;
import com.nihaov.knowledge.service.ITalkService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by nihao on 18/4/28.
 */
@Service
public class TalkServiceImpl implements ITalkService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private ITalkDAO talkDAO;
    @Resource
    private IUserDAO userDAO;

    @Override
    public List<TalkVO> getList(Integer from, Integer rows) {
        List<TalkPO> talkPOList = talkDAO.selectLimit(from, rows);
        if(talkPOList.isEmpty()){
            return new ArrayList<>();
        }
        List<Integer> userIds = new ArrayList<>();
        List<TalkVO> voList = talkPOList.stream().map(po -> {
            TalkVO vo = new TalkVO(po);
            if(!userIds.contains(po.getUserId())){
                userIds.add(po.getUserId());
            }
            return vo;
        }).collect(Collectors.toList());
        Map<Integer,UserPO> userPOMap = userDAO.selectByIds(userIds);
        for(TalkVO vo : voList){
            UserPO userPO = userPOMap.get(vo.getUserId());
            if(userPO != null){
                vo.setUser(new UserVO(userPO));
            }
        }
        return voList;
    }

    @Override
    public void addTalk(Integer userId, Long parentId, String content) {
        TalkPO talkPO = new TalkPO();
        talkPO.setUserId(userId);
        talkPO.setParentId(parentId);
        talkPO.setContent(content);
        talkDAO.insert(talkPO);
    }
}
