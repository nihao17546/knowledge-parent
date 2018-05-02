package com.test;

import com.nihaov.knowledge.common.enums.ResourceTypeEnum;
import com.nihaov.knowledge.pojo.vo.ResourceBasicVO;
import com.nihaov.knowledge.pojo.vo.ResourceVO;
import com.nihaov.knowledge.service.IResourceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by nihao on 18/4/18.
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})
public class MainTest {

    @Resource
    private IResourceService resourceServiceSolr;

    @Test
    public void test01(){
        List<ResourceVO> list = resourceServiceSolr.getResourceLimit(1, ResourceTypeEnum.文档, 0, 5);
        System.out.println("---");
    }
    @Test
    public void test02(){
        ResourceVO vo = resourceServiceSolr.getById(1);
        List<ResourceBasicVO> list = resourceServiceSolr.search("虚拟机", 0, 100);
        System.out.println("--");
    }
    @Test
    public void test03(){
    }
}
