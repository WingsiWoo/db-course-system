package com.wingsiwoo.www.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wingsiwoo.www.entity.po.Address;

import java.util.List;

/**
 * @author WingsiWoo
 * @date 2021/11/27
 */
public interface AddressService extends IService<Address> {
    /**
     * 获取所有教学楼号
     * @return 教学楼号
     */
    List<Integer> getAllBuildings();

    /**
     * 获取教学楼内所有课室号
     * @param building 教学楼号
     * @return 课室号
     */
    List<Integer> getBuildingClazz(Integer building);
}
