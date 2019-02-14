package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsSite;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Auther: 段金良
 * @Date: 2019/2/3 0003 12:50
 * @Description:
 */
public interface CmsSiteRepository extends MongoRepository<CmsSite,String> {
}
