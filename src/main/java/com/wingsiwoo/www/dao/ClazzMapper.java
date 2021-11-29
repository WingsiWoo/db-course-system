package com.wingsiwoo.www.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wingsiwoo.www.entity.po.Clazz;
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
public interface ClazzMapper extends BaseMapper<Clazz> {
    /**
     * 根据专业id查询班级
     *
     * @param id 专业id
     * @return 班级信息
     */
    default List<Clazz> getBySpecId(Integer id) {
        QueryWrapper<Clazz> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(Clazz::getSpecId, id);
        return selectList(wrapper);
    }
}
