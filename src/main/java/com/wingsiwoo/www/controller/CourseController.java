package com.wingsiwoo.www.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wingsiwoo.www.auth.result.CommonResult;
import com.wingsiwoo.www.entity.bo.CoursePageBo;
import com.wingsiwoo.www.entity.bo.CreateCourseBo;
import com.wingsiwoo.www.entity.bo.UpdateGradeBo;
import com.wingsiwoo.www.service.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author WingsiWoo
 * @since 2021-11-11
 */
@RestController
@RequestMapping("/api/course")
public class CourseController {
    @Resource
    private CourseService courseService;

    /**
     * 导出课程学生成绩信息
     *
     * @param courseId 课程id
     * @return 学生成绩excel
     */
    @GetMapping("/exportGradeExcel")
    public ResponseEntity<byte[]> exportGradeExcel(@NotNull @RequestParam("courseId") Integer courseId) {
        return courseService.exportGradeExcel(courseId);
    }

    /**
     * 导出选课学生名单
     *
     * @param courseId 课程id
     * @return 学生信息
     */
    @GetMapping("/exportStudentCourseInfo")
    public ResponseEntity<byte[]> exportStudentCourseInfo(@NotNull @RequestParam("courseId") Integer courseId) {
        return courseService.exportStudentCourseInfo(courseId);
    }

    /**
     * 导入课程学生成绩信息
     *
     * @return 导入结果
     */
    @PostMapping("/teacherImportGrade")
    public CommonResult<Void> teacherImportGrade(@NotNull(message = "文件不可为空") @RequestParam("file") MultipartFile file,
                                                 @NotNull(message = "教师id不可为空") @RequestParam("teacherId") Integer teacherId,
                                                 @NotNull(message = "课程id不可为空") @RequestParam("courseId") Integer courseId) {
        return CommonResult.autoResult(courseService.teacherImportGrade(file, teacherId, courseId));
    }

    /**
     * 导出学生成绩excel模板
     */
    @GetMapping("/exportGradeTemplate")
    public ResponseEntity<byte[]> exportGradeTemplate() {
        return courseService.exportGradeTemplate();
    }

    /**
     * 修改学生成绩信息
     *
     * @param updateGradeBo updateGradeBo
     * @return 修改是否成功
     */
    @PostMapping("/updateStudentGrade")
    public CommonResult<Void> updateStudentGrade(@Validated @RequestBody UpdateGradeBo updateGradeBo) {
        return CommonResult.autoResult(courseService.updateStudentGrade(updateGradeBo));
    }

    /**
     * 分页展示所有课程信息
     *
     * @return CommonResult<Page < CoursePageBo>>
     */
    @GetMapping("/showCoursesInPage")
    public CommonResult<Page<CoursePageBo>> showCoursesInPage() {
        return CommonResult.operateSuccess(courseService.showCoursesInPage());
    }

    /**
     * 分页展示用户选择/任教的所有课程
     *
     * @return CommonResult<Page < CoursePageBo>>
     */
    @PostMapping("/showSelectedCoursesInPage")
    public CommonResult<Page<CoursePageBo>> showSelectedCoursesInPage(@NotNull(message = "用户id不可为空") @RequestParam("userId") Integer userId) {
        return CommonResult.operateSuccess(courseService.showSelectedCoursesInPage(userId));
    }

    /**
     * 学生选课
     *
     * @return 选课是否成功
     */
    @PostMapping("/selectCourse")
    public CommonResult<Void> selectCourse(@NotNull(message = "学生id不可为空") @RequestParam("studentId") Integer studentId,
                                           @NotNull(message = "课程id不可为空") @RequestParam("courseId") Integer courseId) {
        return CommonResult.autoResult(courseService.selectCourse(studentId, courseId));
    }

    /**
     * 教师开课
     *
     * @return 开课是否成功
     */
    @PostMapping("/createCourse")
    public CommonResult<Void> createCourse(@Validated @RequestBody CreateCourseBo createCourseBo) {
        return CommonResult.autoResult(courseService.createCourse(createCourseBo));
    }

    /**
     * 根据课程名称模糊查询
     * @param name 课程名称关键字，%name%查询
     * @return CommonResult<Page<CoursePageBo>>
     */
    @GetMapping("/fuzzyCourseName")
    public CommonResult<Page<CoursePageBo>> fuzzyCourseName(@NotEmpty(message = "关键字不可为空") @RequestParam String name) {
        return CommonResult.operateSuccess(courseService.fuzzyCourseName(name));
    }
}

