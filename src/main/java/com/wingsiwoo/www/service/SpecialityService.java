package com.wingsiwoo.www.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wingsiwoo.www.entity.bo.SpecialityBo;
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
     * 分页查询所有专业信息
     *
     * @return 专业信息
     */
    Page<SpecialityPageBo> getAllSpecInfo();

    /**
     * 创建专业
     *
     * @param specialityBo 专业信息
     * @return 创建是否成功
     */
    boolean addSpeciality(SpecialityBo specialityBo);

    /**
     * 删除专业
     *
     * @param id 专业id
     * @return 删除是否成功
     */
    boolean deleteSpeciality(Integer id);

    /**
     * 更改专业信息
     *
     * @param specialityBo 专业信息
     * @return 更改是否成功
     */
    boolean updateSpeciality(SpecialityBo specialityBo);
}
