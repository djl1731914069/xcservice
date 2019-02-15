package com.xuecheng.api.course;

import com.xuecheng.framework.domain.cms.ResponseData;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;

public interface TeachplanControllerApi {
    TeachplanNode getTeachTree(String courseId);
    ResponseData findCourseList(Integer page,Integer size);
    ResponseData findCategoryList();
    ResponseData findByDType(String dType);
}
