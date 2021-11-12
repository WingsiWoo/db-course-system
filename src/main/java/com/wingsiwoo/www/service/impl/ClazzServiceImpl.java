package com.wingsiwoo.www.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wingsiwoo.www.constant.SexConstant;
import com.wingsiwoo.www.dao.*;
import com.wingsiwoo.www.entity.bo.UserExcelBo;
import com.wingsiwoo.www.entity.po.Clazz;
import com.wingsiwoo.www.entity.po.College;
import com.wingsiwoo.www.entity.po.Speciality;
import com.wingsiwoo.www.entity.po.User;
import com.wingsiwoo.www.service.ClazzService;
import com.wingsiwoo.www.util.ExcelUtil;
import com.wingsiwoo.www.util.NameUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Objects;
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
public class ClazzServiceImpl extends ServiceImpl<ClazzMapper, Clazz> implements ClazzService {
    @Resource
    private ClazzMapper clazzMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private CollegeMapper collegeMapper;
    @Resource
    private SpecialityMapper specialityMapper;
    @Resource
    private YearMapper yearMapper;

    @Override
    public ResponseEntity<byte[]> exportClazzInfo(Integer clazzId) {
        Clazz clazz = getById(clazzId);
        Assert.isTrue(Objects.nonNull(clazz), "班级不存在");
        // 搜索完整班级名相关信息
        Integer year = yearMapper.selectById(clazz.getYearId()).getYear();
        Speciality speciality = specialityMapper.selectById(clazz.getSpecId());
        College college = collegeMapper.selectById(speciality.getCollegeId());

        List<User> users = userMapper.selectBatchByClazzId(clazzId);
        List<UserExcelBo> excelBoList = UserExcelBo.transformToUserExcelBo(users);
        String clazzName = NameUtil.getClazzName(year, college.getName(), speciality.getName(), clazz.getClazzIndex());

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ExcelUtil.writeToExcel(clazzName + "学生信息",
                byteArrayOutputStream, UserExcelBo.class, null, excelBoList, 1, 0);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentDispositionFormData("attachment", clazzName + "学生信息");
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<>(byteArrayOutputStream.toByteArray(), httpHeaders, HttpStatus.CREATED);
    }
}
