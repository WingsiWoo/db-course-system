package com.wingsiwoo.www.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wingsiwoo.www.entity.po.Year;
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
public interface YearMapper extends BaseMapper<Year> {
    /**
     * 根据年级号查找
     *
     * @param grade 年级号
     * @return Year
     */
    default Year selectByGrade(Integer grade) {
        QueryWrapper<Year> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(Year::getGrade, grade);
        return selectOne(wrapper);
    }
}
