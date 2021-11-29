package com.wingsiwoo.www.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wingsiwoo.www.entity.po.Address;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author WingsiWoo
 * @date 2021/11/27
 */
@Mapper
public interface AddressMapper extends BaseMapper<Address> {
    /**
     * 获取教学楼内所有课室
     *
     * @param building 教学楼号
     * @return 课室信息
     */
    default List<Address> getByBuilding(Integer building) {
        QueryWrapper<Address> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(Address::getBuilding, building);
        return selectList(wrapper);
    }

    /**
     * 根据楼号和课室号查询课室是否存在
     *
     * @param building 楼号
     * @param num      课室号
     * @return 课室信息
     */
    default Address getByInfo(Integer building, Integer num) {
        QueryWrapper<Address> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(Address::getBuilding, building)
                .eq(Address::getNum, num);
        return selectOne(wrapper);
    }
}
