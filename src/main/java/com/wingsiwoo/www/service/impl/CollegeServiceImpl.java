package com.wingsiwoo.www.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wingsiwoo.www.dao.CollegeMapper;
import com.wingsiwoo.www.dao.SpecialityMapper;
import com.wingsiwoo.www.entity.bo.CollegeBo;
import com.wingsiwoo.www.entity.po.College;
import com.wingsiwoo.www.service.CollegeService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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
    @Resource
    private SpecialityMapper specialityMapper;

    @Override
    public Page<College> getAllCollegeInfo() {
        Page<College> page = new Page<>(1, 10);
        collegeMapper.selectPage(page, null);
        return page;
    }

    @Override
    public boolean addCollege(CollegeBo collegeBo) {
        Assert.isNull(collegeMapper.getByName(collegeBo.getName()), "学院名称不可重复");
        College college = new College();
        college.setName(collegeBo.getName());
        college.setIntroduction(collegeBo.getIntroduction());
        return save(college);
    }

    @Override
    public boolean deleteCollege(Integer id) {
        College college = getById(id);
        Assert.notNull(college, "该学院id不存在");
        // 如果该学院下有专业则不允许删除
        Assert.isTrue(CollectionUtils.isEmpty(specialityMapper.getByCollegeId(id)), "该学院下有所属专业，不允许删除");
        return collegeMapper.deleteById(id) == 1;
    }

    @Override
    public boolean updateCollege(College college) {
        Assert.notNull(college.getId(), "学院id不可为空");
        Assert.notNull(college.getName(), "学院名称不可为空");
        College oldCollege = getById(college.getId());
        // 如果想要修改学院名称
        if(!oldCollege.getName().equals(college.getName())) {
            Assert.isNull(collegeMapper.getByName(college.getName()), "学院名称不可重复");
        }
        Assert.notNull(getById(college.getId()), "学院不存在");
        return updateById(college);
    }
}
