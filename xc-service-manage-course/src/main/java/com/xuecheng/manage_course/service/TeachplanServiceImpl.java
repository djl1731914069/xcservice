package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.cms.ResponseData;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.ext.CategoryNode;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.system.SysDictionary;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_course.dao.CmsDictionaryRepository;
import com.xuecheng.manage_course.dao.CourseBaseRepository;
import com.xuecheng.manage_course.dao.CourseMapper;
import com.xuecheng.manage_course.dao.TeachplanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    CourseBaseRepository courseBaseRepository;
    @Autowired
    CmsDictionaryRepository cmsDictionaryRepository;

    public  TeachplanNode getTeachTree(String courseId){
      return  teachplanMapper.getTeachTree(courseId);
    }


    public ResponseData findCourseList(Integer page, Integer size) {
        Pageable pageable = new PageRequest(page-1, size);
        Page<CourseBase> pages =  courseBaseRepository.findAll(pageable);
        ResponseData r = new ResponseData();
        QueryResult q = new QueryResult();
        q.setList(pages.getContent());
        q.setTotal(pages.getTotalElements());
        r.setResult(q);
        return  r;
    }

    public ResponseData findCategoryList() {
        ResponseData r = new ResponseData();
        CategoryNode categoryList = courseMapper.findCategoryList();
        r.setResult(categoryList);
        return r;
    }

    public ResponseData findByDType(String dtype) {
        ResponseData r = new ResponseData();
        SysDictionary byDType = cmsDictionaryRepository.findByDType(dtype);
        r.setResult(byDType.getDValue());
        return r;
    }

    public ResponseData add(CourseBase courseBase) {
        CourseBase save = courseBaseRepository.save(courseBase);
        return  new ResponseData();
    }
}
