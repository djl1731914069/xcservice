package com.xuecheng.manage_cms.controller;

import com.xuecheng.api.cms.CmsTemplateControllerApi;
import com.xuecheng.framework.domain.cms.ResponseData;
import com.xuecheng.manage_cms.service.CmsTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: 段金良
 * @Date: 2019/2/3 0003 17:40
 * @Description:
 */
@RestController
@RequestMapping("/cms/temp")
public class CmsTemplateController implements CmsTemplateControllerApi {
    @Autowired
    CmsTemplateService cmsTemplateService;
    @GetMapping("/getTemple")
    public ResponseData getTemple() {
        return cmsTemplateService.getTemp();
    }
}
