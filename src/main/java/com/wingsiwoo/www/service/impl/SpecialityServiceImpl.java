package com.wingsiwoo.www.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wingsiwoo.www.dao.CollegeMapper;
import com.wingsiwoo.www.dao.SpecialityMapper;
import com.wingsiwoo.www.entity.bo.SpecialityPageBo;
import com.wingsiwoo.www.entity.po.College;
import com.wingsiwoo.www.entity.po.Speciality;
import com.wingsiwoo.www.service.SpecialityService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

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

    @Override
    public Page<SpecialityPageBo> getAllSpecInfo() {
        Page<SpecialityPageBo> page = new Page<>(1, 10);
        List<Speciality> specialities = specialityMapper.selectList(null);
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
        return page;
    }
}
