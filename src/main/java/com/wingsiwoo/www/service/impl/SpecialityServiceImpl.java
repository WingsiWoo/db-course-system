package com.wingsiwoo.www.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wingsiwoo.www.dao.ClazzMapper;
import com.wingsiwoo.www.dao.CollegeMapper;
import com.wingsiwoo.www.dao.SpecialityMapper;
import com.wingsiwoo.www.entity.bo.SpecialityBo;
import com.wingsiwoo.www.entity.bo.SpecialityPageBo;
import com.wingsiwoo.www.entity.po.College;
import com.wingsiwoo.www.entity.po.Speciality;
import com.wingsiwoo.www.service.SpecialityService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author WingsiWoo
 * @since 2021-11-11
 */
@Service
public class SpecialityServiceImpl extends ServiceImpl<SpecialityMapper, Speciality> implements SpecialityService {
    @Resource
    private SpecialityMapper specialityMapper;
    @Resource
    private CollegeMapper collegeMapper;
    @Resource
    private ClazzMapper clazzMapper;

    @Override
    public Page<SpecialityPageBo> getAllSpecInfo() {
        Page<SpecialityPageBo> page = new Page<>(1, 10);
        List<Speciality> specialities = specialityMapper.selectList(null);
        if (CollectionUtils.isNotEmpty(specialities)) {
            Map<Integer, String> collegeMap = collegeMapper.selectBatchIds(specialities.stream().map(Speciality::getCollegeId).collect(Collectors.toList()))
                    .stream().collect(Collectors.toMap(College::getId, College::getName));
            List<SpecialityPageBo> collect = specialities.stream().map(speciality -> {
                SpecialityPageBo specialityPageBo = new SpecialityPageBo();
                BeanUtils.copyProperties(speciality, specialityPageBo);
                specialityPageBo.setCollegeName(collegeMap.get(speciality.getCollegeId()));
                return specialityPageBo;
            }).collect(Collectors.toList());
            page.setRecords(collect);
            page.setTotal(collect.size());
        }
        return page;
    }

    @Override
    public boolean addSpeciality(SpecialityBo specialityBo) {
        Assert.isNull(specialityMapper.getByName(specialityBo.getName()), "专业名称不可重复");
        Assert.notNull(collegeMapper.selectById(specialityBo.getCollegeId()), "学院不存在");
        Speciality speciality = new Speciality();
        speciality.setName(specialityBo.getName());
        speciality.setIntroduction(specialityBo.getIntroduction());
        speciality.setCollegeId(specialityBo.getCollegeId());
        return save(speciality);
    }

    @Override
    public boolean deleteSpeciality(Integer id) {
        Speciality speciality = getById(id);
        Assert.notNull(speciality, "该专业id不存在");
        // 如果该专业下有所属班级则不允许删除
        Assert.isTrue(CollectionUtils.isEmpty(clazzMapper.getBySpecId(id)), "该专业下有所属班级，不允许删除");
        return specialityMapper.deleteById(id) == 1;
    }

    @Override
    public boolean updateSpeciality(Speciality speciality) {
        Assert.notNull(speciality.getId(), "专业id不可为空");
        Assert.notNull(speciality.getName(), "专业名称不可为空");
        Speciality oldSpeciality = getById(speciality.getId());
        if (!oldSpeciality.getName().equals(speciality.getName())) {
            Assert.isNull(specialityMapper.getByName(speciality.getName()), "专业名称不可重复");
        }
        Assert.notNull(getById(speciality.getId()), "专业不存在");
        return updateById(speciality);
    }
}
