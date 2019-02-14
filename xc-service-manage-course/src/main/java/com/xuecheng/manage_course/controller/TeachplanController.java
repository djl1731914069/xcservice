package com.xuecheng.manage_course.controller;

import com.xuecheng.api.course.TeachplanControllerApi;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.manage_course.service.TeachplanServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
