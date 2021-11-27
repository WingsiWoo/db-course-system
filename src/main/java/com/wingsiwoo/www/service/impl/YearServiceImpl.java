package com.wingsiwoo.www.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wingsiwoo.www.constant.GraduateTagConstant;
import com.wingsiwoo.www.dao.YearMapper;
import com.wingsiwoo.www.entity.bo.YearBo;
import com.wingsiwoo.www.entity.po.Year;
import com.wingsiwoo.www.service.YearService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Comparator;
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
public class YearServiceImpl extends ServiceImpl<YearMapper, Year> implements YearService {
    @Resource
    private YearMapper yearMapper;

    @Override
    public Page<Year> getYearInfoInPage() {
        Page<Year> page = new Page<>(1, 10);
        yearMapper.selectPage(page, null);
        // 按照年级号降序排序
        page.setRecords(page.getRecords().stream().sorted(Comparator.comparing(Year::getGrade).reversed()).collect(Collectors.toList()));
        return page;
    }

    @Override
    public boolean addYear(Integer grade) {
        Assert.isNull(yearMapper.selectByGrade(grade), "年级号不可重复");
        Year newYear = new Year();
        newYear.setGrade(grade);
        // 新增年级默认为未毕业
        newYear.setGraduate(GraduateTagConstant.NOT_GRADUATED);
        return save(newYear);
    }

    @Override
    public boolean updateYear(YearBo yearBo) {
        Year year = getById(yearBo.getId());
        Assert.notNull(year, "年级id不存在");
        year.setGraduate(yearBo.getGraduate());
        return updateById(year);
    }
}
