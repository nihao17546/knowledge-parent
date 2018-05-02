package com.nihaov.knowledge.dao;

import com.nihaov.knowledge.pojo.po.UserPO;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by nihao on 18/4/26.
 */
public interface IUserDAO {
    UserPO selectByOpenId(@Param("openId") String openId);
    int insert(UserPO userPO);
    int update(UserPO userPO);
    @MapKey("id")
    Map<Integer,UserPO> selectByIds(@Param("list") List<Integer> list);
}
