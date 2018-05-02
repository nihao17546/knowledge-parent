package com.nihaov.knowledge.service.impl;

import com.nihaov.knowledge.dao.IFocusDAO;
import com.nihaov.knowledge.dao.IResourceDAO;
import com.nihaov.knowledge.pojo.po.FocusPO;
import com.nihaov.knowledge.pojo.po.ResourcePO;
import com.nihaov.knowledge.pojo.vo.FocusVO;
import com.nihaov.knowledge.pojo.vo.ResourceVO;
import com.nihaov.knowledge.service.IFocusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by nihao on 18/4/26.
 */
@Service
public class FocusServiceImpl implements IFocusService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private IFocusDAO focusDAO;
    @Resource
    private IResourceDAO resourceDAO;

    @Override
    public List<FocusVO> getFocuses() {
        List<FocusPO> poList = focusDAO.selectOrderByPosition();
        List<Integer> resourceIds = new ArrayList<>();
        List<FocusVO> voList = poList.stream().map(po -> {
            FocusVO vo = new FocusVO(po);
            resourceIds.add(po.getResourceId());
            return vo;
        }).collect(Collectors.toList());
        Map<Integer,ResourcePO> resourcePOMap = resourceDAO.selectByIds(resourceIds);
        for(FocusVO vo : voList){
            ResourcePO resourcePO = resourcePOMap.get(vo.getResourceId());
            resourcePO.setContent(null);
            vo.setResource(new ResourceVO(resourcePO));
        }
        return voList;
    }
}
