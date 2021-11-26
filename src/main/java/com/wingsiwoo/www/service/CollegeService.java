package com.wingsiwoo.www.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wingsiwoo.www.entity.bo.CollegeBo;
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

    /**
     * 创建学院
     *
     * @param collegeBo 学院信息
     * @return 创建是否成功
     */
    boolean addCollege(CollegeBo collegeBo);

    /**
     * 删除学院
     *
     * @param id 学院id
     * @return 删除是否成功
     */
    boolean deleteCollege(Integer id);

    /**
     * 更改学院信息
     *
     * @param collegeBo 学院信息
     * @return 更改是否成功
     */
    boolean updateCollege(CollegeBo collegeBo);
}
