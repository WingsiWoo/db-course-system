package com.wingsiwoo.www.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wingsiwoo.www.entity.bo.YearBo;
import com.wingsiwoo.www.entity.po.Year;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author WingsiWoo
 * @since 2021-11-11
 */
public interface YearService extends IService<Year> {
    /**
     * 分页查询年级信息
     *
     * @return Page<Year>
     */
    Page<Year> getYearInfoInPage();

    /**
     * 新增年级-默认为未毕业
     *
     * @param grade 年级号
     * @return 新增结果
     */
    boolean addYear(Integer grade);

    /**
     * 更改年级毕业表示
     *
     * @param yearBo yearBo
     * @return 更改结果
     */
    boolean updateYear(YearBo yearBo);
}
