package com.wingsiwoo.www.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wingsiwoo.www.entity.po.College;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author WingsiWoo
 * @since 2021-11-11
 */
@Mapper
public interface CollegeMapper extends BaseMapper<College> {
    /**
     * 根据学院名称查找
     *
     * @param name 学院名称
     * @return 学院
     */
    default College getByName(String name) {
        QueryWrapper<College> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(College::getName, name);
        return selectOne(wrapper);
    }
}
