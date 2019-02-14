package com.xuecheng.manage_cms.service.impl;

import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.manage_cms.dao.CmsConfigRepository;
import com.xuecheng.manage_cms.service.CmsConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Auther: 段金良
 * @Date: 2019/2/1 0001 20:17
 * @Description:
 */
@Service
public class CmsCofigServiceImpl implements CmsConfigService {
    @Autowired
    CmsConfigRepository cmsConfigRepository;
    @Override
    public CmsConfig getConfigById(String id) {
        Optional<CmsConfig> optional = cmsConfigRepository.findById(id);
        if(optional.isPresent()){
            return optional.get();
        }
        return  null;
    }
}
