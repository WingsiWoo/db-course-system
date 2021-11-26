package com.wingsiwoo.www.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wingsiwoo.www.dao.CollegeMapper;
import com.wingsiwoo.www.entity.bo.CollegeBo;
import com.wingsiwoo.www.entity.po.College;
import com.wingsiwoo.www.service.CollegeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author WingsiWoo
 * @since 2021-11-11
 */
@Service
public class CollegeServiceImpl extends ServiceImpl<CollegeMapper, College> implements CollegeService {
    @Resource
    private CollegeMapper collegeMapper;

    @Override
    public Page<College> getAllCollegeInfo() {
        Page<College> page = new Page<>(1, 10);
        collegeMapper.selectPage(page, null);
        return page;
    }

    @Override
    public boolean addCollege(CollegeBo collegeBo) {
        return false;
    }

    @Override
    public boolean deleteCollege(Integer id) {
        return false;
    }

    @Override
    public boolean updateCollege(CollegeBo collegeBo) {
        return false;
    }
}
