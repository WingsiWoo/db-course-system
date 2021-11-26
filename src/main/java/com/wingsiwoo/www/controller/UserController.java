package com.wingsiwoo.www.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wingsiwoo.www.auth.result.CommonResult;
import com.wingsiwoo.www.entity.bo.LoginBo;
import com.wingsiwoo.www.entity.bo.LoginResultBo;
import com.wingsiwoo.www.entity.bo.ShowUserBo;
import com.wingsiwoo.www.entity.bo.UpdatePasswordBo;
import com.wingsiwoo.www.service.UserService;
import org.hibernate.validator.constraints.Range;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author WingsiWoo
 * @since 2021-11-11
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Resource
    private UserService userService;

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public CommonResult<LoginResultBo> login(@Validated @RequestBody LoginBo loginBo) {
        return CommonResult.operateSuccess(userService.login(loginBo));
    }

    /**
     * 导入用户信息（同一个excel中只可导入相同角色的）
     */
    @PostMapping("/importUserInfo")
    public CommonResult<Void> importUserInfo(@NotNull(message = "文件不可为空") @RequestParam("file") MultipartFile file,
                                             @NotNull(message = "角色id不可为空") @RequestParam("roleId") Integer roleId) {
        return CommonResult.autoResult(userService.importUserInfo(file, roleId));
    }

    /**
     * 获取用户信息模板
     *
     * @return 用户信息模板
     */
    @GetMapping("/exportUserTemplate")
    public ResponseEntity<byte[]> exportUserTemplate() {
        return userService.exportUserTemplate();
    }

    /**
     * 修改密码
     *
     * @param updatePasswordBo updatePasswordBo
     * @return 修改结果
     */
    @PostMapping("/updatePassword")
    public CommonResult<Void> updatePassword(@Validated @RequestBody UpdatePasswordBo updatePasswordBo) {
        return CommonResult.autoResult(userService.updatePassword(updatePasswordBo));
    }

    /**
     * 分页展示所有用户信息
     * 1-学生，2-教师。3-全部
     * @return CommonResult<Page<ShowUserBo>>
     */
    @GetMapping("/showAllUserInPage")
    public CommonResult<Page<ShowUserBo>> showAllUserInPage(@NotNull(message = "角色类型不可为空") @Range(min = 1, max = 3) Integer roleId) {
        return CommonResult.operateSuccess(userService.showAllUserInPage(roleId));
    }
}

