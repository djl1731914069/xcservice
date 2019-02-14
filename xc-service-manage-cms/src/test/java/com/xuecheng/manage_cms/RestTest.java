package com.xuecheng.manage_cms;

import com.xuecheng.framework.domain.cms.CmsConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

/**
 * @Auther: 段金良
 * @Date: 2019/2/1 0001 20:33
 * @Description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RestTest {
    @Autowired
    RestTemplate restTemplate;
    @Test
    public  void test(){
        ResponseEntity<CmsConfig> entity = restTemplate.getForEntity("http://localhost:31001/cms/config/get/5a791725dd573c3574ee333f", CmsConfig.class);
        CmsConfig body = entity.getBody();
        System.out.println(body);


    }
}
