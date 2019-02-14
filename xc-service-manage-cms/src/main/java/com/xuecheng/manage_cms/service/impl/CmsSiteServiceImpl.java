package com.xuecheng.manage_cms.service.impl;

import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.domain.cms.ResponseData;
import com.xuecheng.manage_cms.dao.CmsSiteRepository;
import com.xuecheng.manage_cms.service.CmsSiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: 段金良
 * @Date: 2019/2/3 0003 12:48
 * @Description:
 */
@Service
public class CmsSiteServiceImpl implements CmsSiteService {
    @Autowired
    CmsSiteRepository cms;

    @Override
    public ResponseData getAll() {
        List<CmsSite> all = cms.findAll();
        ResponseData r = new ResponseData();
        r.setCode(0);
        r.setResult(all);
        return r;
    }
}
