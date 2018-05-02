package com.nihaov.knowledge.service.impl;

import com.nihaov.knowledge.common.constants.RedisKeyConstants;
import com.nihaov.knowledge.common.enums.ResourceTypeEnum;
import com.nihaov.knowledge.dao.IResourceDAO;
import com.nihaov.knowledge.pojo.po.ResourcePO;
import com.nihaov.knowledge.pojo.vo.ResourceBasicVO;
import com.nihaov.knowledge.pojo.vo.ResourceVO;
import com.nihaov.knowledge.service.IRedisService;
import com.nihaov.knowledge.service.IResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by nihao on 18/4/14.
 */
@Service("resourceServiceMysql")
public class ResourceServiceImpl implements IResourceService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private IResourceDAO resourceDAO;
    @Resource
    private IRedisService redisService;

    @Override
    public List<ResourceVO> getResourceLimit(Integer catalogId, ResourceTypeEnum type,
                                             Integer from, Integer rows) {
        List<ResourcePO> poList = resourceDAO.selectResourceByCatalogAndType(catalogId, type.getValue(), from, rows);
        return poListToVoList(poList);
    }

    @Override
    public ResourceVO getById(Integer id) {
        return new ResourceVO(resourceDAO.selectById(id));
    }

    @Override
    public List<ResourceBasicVO> search(String keywords, Integer from, Integer rows) {
        throw new RuntimeException("Unavailable");
    }

    @Override
    public List<ResourceVO> getAllResourceByCatalogId(Integer catalogId) {
        List<ResourcePO> poList = resourceDAO.selectAllResourceByCatalog(catalogId);
        Map<String,String> redisMap = redisService.mulGet(RedisKeyConstants.get资源阅读次数Key(catalogId, null));
        List<ResourceVO> voList = poListToVoList(poList);
        for(ResourceVO vo : voList){
            String value = redisMap.get(RedisKeyConstants.get资源阅读次数Key(catalogId, vo.getId()));
            if(value != null){
                vo.setPv(Long.parseLong(value));
            }
        }
        return voList;
    }

    private List<ResourceVO> poListToVoList(List<ResourcePO> poList){
        List<ResourceVO> voList = poList.stream().map(po -> {
            po.setContent(null);
            ResourceVO vo = new ResourceVO(po);
            return vo;
        }).collect(Collectors.toList());
        return voList;
    }
}
