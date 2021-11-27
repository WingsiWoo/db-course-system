package com.wingsiwoo.www.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wingsiwoo.www.dao.*;
import com.wingsiwoo.www.entity.bo.UserExcelBo;
import com.wingsiwoo.www.entity.po.Clazz;
import com.wingsiwoo.www.entity.po.College;
import com.wingsiwoo.www.entity.po.Speciality;
import com.wingsiwoo.www.entity.po.User;
import com.wingsiwoo.www.service.ClazzService;
import com.wingsiwoo.www.service.UserService;
import com.wingsiwoo.www.util.ExcelUtil;
import com.wingsiwoo.www.util.NameUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
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
    private UserMapper userMapper;
    @Resource
    private CollegeMapper collegeMapper;
    @Resource
    private SpecialityMapper specialityMapper;
    @Resource
    private YearMapper yearMapper;
    @Resource
    private UserService userService;

    @Override
    public ResponseEntity<byte[]> exportClazzInfo(Integer clazzId) {
        Clazz clazz = getById(clazzId);
        Assert.isTrue(Objects.nonNull(clazz), "班级不存在");
        // 搜索完整班级名相关信息
        Integer year = yearMapper.selectById(clazz.getYearId()).getGrade();
        Speciality speciality = specialityMapper.selectById(clazz.getSpecId());
        College college = collegeMapper.selectById(speciality.getCollegeId());

        List<User> users = userMapper.selectBatchByClazzId(clazzId);
        List<UserExcelBo> excelBoList = UserExcelBo.transformToUserExcelBo(users);
        String clazzName = NameUtil.getClazzName(year, college.getName(), speciality.getName(), clazz.getClazzIndex());

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ExcelUtil.writeToExcel(clazzName + "学生信息.xlsx",
                byteArrayOutputStream, UserExcelBo.class, null, excelBoList, 1, 0);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentDispositionFormData("attachment", clazzName + "学生信息.xlsx");
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<>(byteArrayOutputStream.toByteArray(), httpHeaders, HttpStatus.CREATED);
    }

    @Override
    public boolean importClazzStudents(MultipartFile file, Integer clazzId) {
        InputStream inputStream = null;
        List<UserExcelBo> excelBoList;
        try {
            inputStream = file.getInputStream();
            excelBoList = ExcelUtil.simpleReadFromExcel(inputStream, 0, UserExcelBo.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("导入失败");
        } finally {
            if (Objects.nonNull(inputStream)) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.warn("关闭资源失败");
                }
            }
        }
        List<String> accounts = excelBoList.stream().map(UserExcelBo::getAccount).collect(Collectors.toList());
        List<User> users = userMapper.selectBatchByAccounts(accounts);
        accounts.removeAll(users.stream().map(User::getAccount).collect(Collectors.toList()));
        Assert.isTrue(CollectionUtils.isEmpty(accounts), "导入学生信息有误：" + StringUtils.join(accounts.toArray(), ","));
        users.forEach(user -> user.setClassId(clazzId));
        return userService.updateBatchById(users);
    }

    @Override
    public ResponseEntity<byte[]> exportClazzTemplate() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ExcelUtil.writeToExcel("班级学生信息模板.xlsx",
                byteArrayOutputStream, UserExcelBo.class, null, new LinkedList<>(), 1, 0);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentDispositionFormData("attachment", "班级学生信息模板.xlsx");
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<>(byteArrayOutputStream.toByteArray(), httpHeaders, HttpStatus.CREATED);
    }
}
