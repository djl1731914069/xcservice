package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.system.SysDictionary;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CmsDictionaryRepository extends MongoRepository<SysDictionary,String> {
   public SysDictionary findByDType(String dType);
}
