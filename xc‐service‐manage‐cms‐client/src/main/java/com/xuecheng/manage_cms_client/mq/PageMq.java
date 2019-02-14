package com.xuecheng.manage_cms_client.mq;

import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson.JSON;
import com.xuecheng.manage_cms_client.service.PageService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Auther: 段金良
 * @Date: 2019/2/5 0005 15:16
 * @Description:监听队列
 */
@Component
public class PageMq {
    @Autowired
    PageService pageService;
    @RabbitListener(queues={"${xuecheng.mq.queue}"})
    public void savePageToServerPath(String message){
        Map map = JSON.parseObject(message, Map.class);
        String pageId = Convert.toStr(map.get("pageId"), "");
        pageService.savePageToServerPath(pageId);
    }
}
