package com.wingsiwoo.www.controller;


import com.wingsiwoo.www.service.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author WingsiWoo
 * @since 2021-11-11
 */
@Controller
@RequestMapping("/api/course")
public class CourseController {
    @Resource
    private CourseService courseService;

    @GetMapping("/exportGradeExcel")
    public ResponseEntity<byte[]> exportGradeExcel(@NotNull @RequestParam("courseId") Integer courseId) {
        return courseService.exportGradeExcel(courseId);
    }

    @GetMapping("/exportStudentCourseInfo")
    public ResponseEntity<byte[]> exportStudentCourseInfo(@NotNull @RequestParam("courseId") Integer courseId) {
        return courseService.exportStudentCourseInfo(courseId);
    }
}

