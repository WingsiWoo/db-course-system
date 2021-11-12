package com.wingsiwoo.www.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wingsiwoo.www.entity.bo.CoursePageBo;
import com.wingsiwoo.www.entity.bo.CreateCourseBo;
import com.wingsiwoo.www.entity.bo.ImportGradeExcelBo;
import com.wingsiwoo.www.entity.bo.UpdateGradeBo;
import com.wingsiwoo.www.entity.po.Course;
import org.springframework.http.ResponseEntity;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author WingsiWoo
 * @since 2021-11-11
 */
public interface CourseService extends IService<Course> {

    /**
     * 导出课程成绩信息
     *
     * @param courseId 课程id
     * @return 课程成绩excel
     */
    ResponseEntity<byte[]> exportGradeExcel(Integer courseId);

    /**
     * 导出选修本门课程学生信息
     *
     * @param courseId 课程id
     * @return 选修本门课程学生信息excel
     */
    ResponseEntity<byte[]> exportStudentCourseInfo(Integer courseId);

    /**
     * 导入学生成绩信息
     *
     * @return 导入结果
     */
    boolean teacherImportGrade(ImportGradeExcelBo excelBo);

    /**
     * 导出学生成绩excel模板
     *
     * @return ResponseEntity<byte [ ]>
     */
    ResponseEntity<byte[]> exportGradeTemplate();

    /**
     * 更新学生成绩
     *
     * @param updateGradeBo updateGradeBo
     * @return 更新结果
     */
    boolean updateStudentGrade(UpdateGradeBo updateGradeBo);

    /**
     * 分页查询所有课程
     *
     * @return Page<CoursePageBo>
     */
    Page<CoursePageBo> showCoursesInPage();

    /**
     * 分页查询用户相关课程
     *
     * @param userId 用户id
     * @return Page<CoursePageBo>
     */
    Page<CoursePageBo> showSelectedCoursesInPage(Integer userId);

    /**
     * 学生选课
     *
     * @param studentId 学生id
     * @param courseId  课程id
     * @return 选课结果
     */
    boolean selectCourse(Integer studentId, Integer courseId);

    /**
     * 教师开课
     *
     * @param createCourseBo createCourseBo
     * @return 开课结果
     */
    boolean createCourse(CreateCourseBo createCourseBo);
}
