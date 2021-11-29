package com.wingsiwoo.www.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wingsiwoo.www.entity.bo.StudentGradeExcelBo;
import com.wingsiwoo.www.entity.po.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author WingsiWoo
 * @since 2021-11-11
 */
@Mapper
public interface CourseMapper extends BaseMapper<Course> {
    /**
     * 查询教师授课
     *
     * @param teacherId 教师id
     * @return 授课信息
     */
    default List<Course> selectByTeacherId(Integer teacherId) {
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(Course::getTeacherId, teacherId);
        return selectList(wrapper);
    }

    /**
     * 根据课程名称模糊查询
     *
     * @param name 查询关键字
     * @return 课程信息
     */
    default List<Course> selectLikeByName(String name) {
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .like(Course::getName, name);
        return selectList(wrapper);
    }

    /**
     * 联表查询学生成绩（学号-姓名-成绩）
     *
     * @param courseId 课程id
     * @return List<StudentGradeExcelBo>
     */
    @Select("SELECT u.name, account, grade FROM student_course sc, user u WHERE sc.student_id = u.id AND sc.course_id = #{courseId}")
    @Results({
            @Result(column = "account", property = "account"),
            @Result(column = "name", property = "name"),
            @Result(column = "grade", property = "grade")
    })
    List<StudentGradeExcelBo> selectStudentGrade(Integer courseId);
}
