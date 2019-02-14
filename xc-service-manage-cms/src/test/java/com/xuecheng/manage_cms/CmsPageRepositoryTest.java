package com.xuecheng.manage_cms;

/**
 * @Auther: 段金良
 * @Date: 2019/1/30 0030 00:17
 * @Description:
 */

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsPageRepositoryTest {
    @Autowired
    CmsPageRepository cmsPageRepository;
    //分页测试
    @Test
    public void testFindPage() {
        int page = 0;//从0开始
        int size = 10;//每页记录数
        Pageable pageable = PageRequest.of(page,size);
        Page<CmsPage> all = cmsPageRepository.findAll(pageable);
        System.out.println(all);
    }

    @Test
    public void add() {
        CmsPage cmsPage = new CmsPage();
        cmsPage.setPageId("000000001");
        cmsPageRepository.save(cmsPage);
    }

    @Test
    public void del() {
        cmsPageRepository.deleteById("000000001");
    }

    @Test
    public void update() {
        CmsPage cmsPage2 = new CmsPage();
        cmsPage2.setPageId("000000001");
        Optional<CmsPage> cmsPage = cmsPageRepository.findOne(Example.of(cmsPage2));
        if (cmsPage.isPresent()){
            CmsPage cmsPage1 = cmsPage.get();
            cmsPage1.setDataUrl("test///");
            cmsPageRepository.save(cmsPage1);
        }
    }
    @Test
    public void findby(){
        Pageable p = new PageRequest(1,1);
        ExampleMatcher exampleMatcher = ExampleMatcher.matching();
        //不设置条件匹配器直接在set中设置值得话按照精确匹配
        CmsPage cmsPage = new CmsPage();
        cmsPage.setPageAliase("轮播");
        exampleMatcher= exampleMatcher.withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());
        Example<CmsPage> example= Example.of(cmsPage,exampleMatcher);
        Page<CmsPage> cmsPages = cmsPageRepository.findAll(example, p);
        System.out.println(cmsPages.getTotalElements());
    }

}
