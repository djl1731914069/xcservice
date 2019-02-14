package com.xuecheng.manage_cms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @Auther: 段金良
 * @Date: 2019/2/1 0001 20:28
 * @Description:
 */
@Configuration
public class HttpConfig {
    /**
     * @Description okhttp性能较好
     * @Param []
     * @return org.springframework.web.client.RestTemplate
     **/
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate(new OkHttp3ClientHttpRequestFactory());
    }
}
