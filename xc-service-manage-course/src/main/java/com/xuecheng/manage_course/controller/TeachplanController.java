package com.xuecheng.manage_course.controller;

import com.xuecheng.api.course.TeachplanControllerApi;
import com.xuecheng.framework.domain.cms.ResponseData;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.manage_course.service.TeachplanServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: 段金良
 * @Date: 2019/2/5 0005 23:14
 * @Description:
 */
@RestController
@RequestMapping("/course")
public class TeachplanController implements TeachplanControllerApi {
    @Autowired
    TeachplanServiceImpl teachplanService;
    @Override
    @GetMapping("/teachplan/list/{courseId}")
    public TeachplanNode getTeachTree(@PathVariable("courseId") String courseId) {
        return teachplanService.getTeachTree(courseId);
    }

    @Override
    @GetMapping("/coursebase/list/{page}/{size}")
    public ResponseData findCourseList(@PathVariable("page") Integer page, @PathVariable("size")Integer size) {
        return teachplanService.findCourseList(page,size);
    }

    @Override
    @GetMapping("/category/list")
    public ResponseData findCategoryList() {
        return teachplanService.findCategoryList();
    }

    @Override
    @GetMapping("/dictionary/get/{dtype}")
    public ResponseData findByDType(@PathVariable("dtype") String dtype) {
        return teachplanService.findByDType(dtype);
    }
    @PostMapping("/coursebase/add")
    public ResponseData add(@RequestBody CourseBase courseBase) {
        return teachplanService.add(courseBase);
    }
}
