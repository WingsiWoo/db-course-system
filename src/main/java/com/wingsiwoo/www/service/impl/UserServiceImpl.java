package com.wingsiwoo.www.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wingsiwoo.www.dao.UserMapper;
import com.wingsiwoo.www.dao.UserRoleMapper;
import com.wingsiwoo.www.entity.bo.LoginBo;
import com.wingsiwoo.www.entity.bo.LoginResultBo;
import com.wingsiwoo.www.entity.bo.UpdatePasswordBo;
import com.wingsiwoo.www.entity.bo.UserExcelBo;
import com.wingsiwoo.www.entity.po.User;
import com.wingsiwoo.www.entity.po.UserRole;
import com.wingsiwoo.www.service.UserRoleService;
import com.wingsiwoo.www.service.UserService;
import com.wingsiwoo.www.util.ExcelUtil;
import com.wingsiwoo.www.util.MD5Util;
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

import static com.wingsiwoo.www.constant.SexConstant.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author WingsiWoo
 * @since 2021-11-11
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserRoleMapper userRoleMapper;

    @Resource
    private UserRoleService userRoleService;

    @Override
    public LoginResultBo login(LoginBo loginBo) {
        User user = userMapper.selectByAccount(loginBo.getAccount());
        Assert.notNull(user, "账户不存在");
        Assert.isTrue(MD5Util.checkPassword(loginBo.getPassword(), user.getPassword()), "密码不正确");
        UserRole userRole = userRoleMapper.selectByUserId(user.getId());
        Assert.notNull(userRole, "用户角色关系不存在");
        return new LoginResultBo(user.getId(), loginBo.getAccount(), user.getName(), userRole.getRoleId());
    }

    @Override
    public boolean importUserInfo(MultipartFile file, Integer roleId) {
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

        Assert.notNull(excelBoList, "导入失败");
        // 过滤掉没有填写账户的
        excelBoList = excelBoList.stream().filter(bo -> StringUtils.isNotEmpty(bo.getAccount())).collect(Collectors.toList());
        // 检查工号/学号是否唯一
        List<String> accounts = excelBoList.stream().map(UserExcelBo::getAccount).collect(Collectors.toList());
        Assert.isTrue(CollectionUtils.isEmpty(userMapper.selectBatchByAccounts(accounts)),
                "学号/工号已存在[" + StringUtils.join(accounts.toArray(), ",") + "]");
        // 检查必填信息是否都填写
        List<UserExcelBo> names = excelBoList.stream().filter(bo -> StringUtils.isEmpty(bo.getName())).collect(Collectors.toList());
        Assert.isTrue(CollectionUtils.isEmpty(names), "以下账户的姓名未填写：" +
                StringUtils.join(names.stream().map(UserExcelBo::getAccount).toArray(), ","));
        List<UserExcelBo> sexList = excelBoList.stream().filter(bo -> StringUtils.isEmpty(bo.getSex()) ||
                !(bo.getSex().equals(SEX_CH_MAN) || bo.getSex().equals(SEX_CH_WOMAN))).collect(Collectors.toList());
        Assert.isTrue(CollectionUtils.isEmpty(sexList), "以下账户性别填写错误：" +
                StringUtils.join(sexList.stream().map(UserExcelBo::getAccount).toArray(), ""));

        // 插入用户信息
        List<User> users = excelBoList.stream().map(bo -> {
            User user = new User();
            user.setAccount(bo.getAccount());
            // 密码默认为学号/工号后6位
            Assert.isTrue(bo.getAccount().length() >= 6, "工号/学号" + bo.getAccount() + "小于6位");
            user.setPassword(MD5Util.getMD5String(bo.getAccount().substring(bo.getAccount().length() - 6)));
            user.setName(bo.getName());
            user.setPhone(bo.getPhone());
            // 0-男,1-女
            user.setSex(transform(bo.getSex()));
            return user;
        }).collect(Collectors.toList());
        Assert.isTrue(saveBatch(users), "导入失败");

        // 插入用户-角色信息
        List<UserRole> userRoles = users.stream().map(user -> {
            UserRole userRole = new UserRole();
            userRole.setRoleId(roleId);
            userRole.setUserId(user.getId());
            return userRole;
        }).collect(Collectors.toList());
        return userRoleService.saveBatch(userRoles);
    }

    @Override
    public ResponseEntity<byte[]> exportUserTemplate() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ExcelUtil.writeToExcel("用户信息模板.xlsx", byteArrayOutputStream, UserExcelBo.class, null, new LinkedList<>(), 1, 0);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentDispositionFormData("attachment", "用户信息模板.xlsx");
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<>(byteArrayOutputStream.toByteArray(), httpHeaders, HttpStatus.CREATED);
    }

    @Override
    public boolean updatePassword(UpdatePasswordBo updatePasswordBo) {
        User user = getById(updatePasswordBo.getUserId());
        Assert.notNull(user, "用户不存在");
        Assert.isTrue(MD5Util.checkPassword(updatePasswordBo.getOldPassword(), user.getPassword()), "旧密码错误");
        user.setPassword(MD5Util.getMD5String(updatePasswordBo.getNewPassword()));
        return save(user);
    }
}
