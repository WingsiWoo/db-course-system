package com.wingsiwoo.www.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wingsiwoo.www.entity.po.StudentCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author WingsiWoo
 * @since 2021-11-11
 */
@Mapper
public interface StudentCourseMapper extends BaseMapper<StudentCourse> {

    /**
     * 查询选修该门课程的学生id
     */
    default List<StudentCourse> selectByCourseId(Integer courseId) {
        QueryWrapper<StudentCourse> wrapper = new QueryWrapper<>();
        wrapper.lambda()
               .select(StudentCourse::getStudentId, StudentCourse::getGrade)
               .eq(StudentCourse::getCourseId, courseId);
        return selectList(wrapper);
    }

    /**
     * 查询学生选修的所有课程id
     */
    default List<StudentCourse> selectByStudentId(Integer studentId) {
        QueryWrapper<StudentCourse> wrapper = new QueryWrapper<>();
        wrapper.lambda()
               .select(StudentCourse::getCourseId, StudentCourse::getGrade)
               .eq(StudentCourse::getStudentId, studentId);
        return selectList(wrapper);
    }

    /**
     * 检查学生是否选修课程
     * @param courseId 课程id
     * @param studentId 学生id
     * @return 是否选修
     */
    default StudentCourse selectRelation(Integer courseId, Integer studentId) {
        QueryWrapper<StudentCourse> wrapper = new QueryWrapper<>();
        wrapper.lambda()
               .eq(StudentCourse::getCourseId, courseId)
               .eq(StudentCourse::getStudentId, studentId);
        return selectOne(wrapper);
    }
}
