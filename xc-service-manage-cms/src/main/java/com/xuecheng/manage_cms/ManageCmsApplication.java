package com.xuecheng.manage_cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * @Auther: 段金良
 * @Date: 2019/1/29 0029 20:21
 * @Description:
 */
@EntityScan("com.xuecheng.framework.domain.cms")//扫描实体
@ComponentScan(basePackages={"com.xuecheng.api"})//扫
@ComponentScan(basePackages={"com.xuecheng.manage_cms"})//扫描本项目下的所有类
@ComponentScan(basePackages={"com.xuecheng.framework.exception"})//扫描本项目下的所有类
@SpringBootApplication
public class ManageCmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManageCmsApplication.class,args);
    }
}
