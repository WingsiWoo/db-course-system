package com.wingsiwoo.www.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wingsiwoo.www.entity.po.Course;
import org.apache.ibatis.annotations.Mapper;

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
}
