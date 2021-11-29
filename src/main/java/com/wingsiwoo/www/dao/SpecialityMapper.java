package com.wingsiwoo.www.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wingsiwoo.www.entity.po.Speciality;
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
public interface SpecialityMapper extends BaseMapper<Speciality> {
    /**
     * 查询学院下的所有专业
     *
     * @param id 学院id
     * @return 专业
     */
    default List<Speciality> getByCollegeId(Integer id) {
        QueryWrapper<Speciality> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(Speciality::getCollegeId, id);
        return selectList(wrapper);
    }

    /**
     * 根据专业名称查找
     *
     * @param name 专业名称
     * @return 专业
     */
    default Speciality getByName(String name) {
        QueryWrapper<Speciality> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(Speciality::getName, name);
        return selectOne(wrapper);
    }
}
