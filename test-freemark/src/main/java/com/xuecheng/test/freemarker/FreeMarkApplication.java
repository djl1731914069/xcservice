package com.xuecheng.test.freemarker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @Auther: 段金良
 * @Date: 2019/1/31 0031 22:58
 * @Description:
 */
@SpringBootApplication
public class FreeMarkApplication {
    public static void main(String[] args) {
        SpringApplication.run(FreeMarkApplication.class,args);
    }
    @Bean
    public RestTemplate restTemplate(){
        return  new RestTemplate();
    }
}
