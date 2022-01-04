package com.wingsiwoo.www.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wingsiwoo.www.entity.bo.CoursePageBo;
import com.wingsiwoo.www.entity.bo.CreateCourseBo;
import com.wingsiwoo.www.entity.bo.UpdateGradeBo;
import com.wingsiwoo.www.entity.po.Course;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

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
    boolean teacherImportGrade(MultipartFile file, Integer teacherId, Integer courseId);

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
    @Deprecated
    boolean updateStudentGrade(UpdateGradeBo updateGradeBo);

    /**
     * 分页查询所有课程
     *
     * @param name 模糊查询关键字
     * @return Page<CoursePageBo>
     */
    Page<CoursePageBo> showCoursesInPage(String name);

    /**
     * 分页查询用户相关课程
     *
     * @param userId 用户id
     * @param name   模糊查询关键字
     * @return Page<CoursePageBo>
     */
    Page<CoursePageBo> showSelectedCoursesInPage(Integer userId, String name);

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

    /**
     * 根据课程名称模糊查询
     *
     * @param name 查询关键字
     * @return Page<CoursePageBo>
     */
    @Deprecated
    Page<CoursePageBo> fuzzyCourseName(String name);
}
