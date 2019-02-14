package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.manage_course.dao.TeachplanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: 段金良
 * @Date: 2019/2/5 0005 23:16
 * @Description:
 */
@Service
public class TeachplanServiceImpl {
    @Autowired
    TeachplanMapper teachplanMapper;

    public  TeachplanNode getTeachTree(String courseId){
      return  teachplanMapper.getTeachTree(courseId);
    }




}
