package com.xuecheng.manage_cms.service.impl;

import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.ResponseData;
import com.xuecheng.manage_cms.dao.CmsTemplateRepository;
import com.xuecheng.manage_cms.service.CmsTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: 段金良
 * @Date: 2019/2/3 0003 17:42
 * @Description:
 */
@Service
public class CmsTemplateServiceImpl implements CmsTemplateService {
    @Autowired
    CmsTemplateRepository cmsTemplateRepository;
    @Override
    public ResponseData getTemp() {
        List<CmsTemplate> all = cmsTemplateRepository.findAll();
        ResponseData responseData = new ResponseData();
        responseData.setResult(all);
        return responseData;
    }
}
