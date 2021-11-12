package com.wingsiwoo.www.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wingsiwoo.www.entity.po.College;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author WingsiWoo
 * @since 2021-11-11
 */
public interface CollegeService extends IService<College> {

    /**
     * 分页查询所有学院信息
     *
     * @return 学院信息
     */
    Page<College> getAllCollegeInfo();
}
