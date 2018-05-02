package com.nihaov.knowledge.service.impl;

import com.nihaov.knowledge.dao.ISubDAO;
import com.nihaov.knowledge.service.ISubService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by nihao on 18/4/28.
 */
@Service
public class SubServiceImpl implements ISubService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private ISubDAO subDAO;

    @Override
    public void add(Integer userId, Integer catalogId) {
        subDAO.insert(userId, catalogId);
    }

    @Override
    public void remove(Integer userId, Integer catalogId) {
        subDAO.delete(userId, catalogId);
    }
}
