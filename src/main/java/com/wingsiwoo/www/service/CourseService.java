package com.wingsiwoo.www.service;

import com.wingsiwoo.www.entity.po.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.http.ResponseEntity;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author WingsiWoo
 * @since 2021-11-11
 */
public interface CourseService extends IService<Course> {

    /**
     * 导出课程成绩信息
     * @param courseId 课程id
     * @return 课程成绩excel
     */
    ResponseEntity<byte[]> exportGradeExcel(Integer courseId);

    /**
     * 导出选修本门课程学生信息
     * @param courseId 课程id
     * @return 选修本门课程学生信息excel
     */
    ResponseEntity<byte[]> exportStudentCourseInfo(Integer courseId);
}
