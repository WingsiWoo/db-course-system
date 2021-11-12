package com.wingsiwoo.www.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wingsiwoo.www.entity.bo.SpecialityPageBo;
import com.wingsiwoo.www.entity.po.Speciality;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author WingsiWoo
 * @since 2021-11-11
 */
public interface SpecialityService extends IService<Speciality> {
    /**
     * 分页查询所有学院信息
     *
     * @return 学院信息
     */
    Page<SpecialityPageBo> getAllSpecInfo();
}
