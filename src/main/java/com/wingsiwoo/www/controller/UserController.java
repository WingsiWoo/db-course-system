package com.wingsiwoo.www.controller;


import com.wingsiwoo.www.auth.result.CommonResult;
import com.wingsiwoo.www.entity.bo.ImportUserExcelBo;
import com.wingsiwoo.www.entity.bo.LoginBo;
import com.wingsiwoo.www.entity.bo.LoginResultBo;
import com.wingsiwoo.www.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author WingsiWoo
 * @since 2021-11-11
 */
@Controller
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
    public CommonResult<Void> importUserInfo(@Validated @RequestBody ImportUserExcelBo excelBo) {
        return CommonResult.autoResult(userService.importUserInfo(excelBo));
    }

    @GetMapping("/exportUserTemplate")
    public ResponseEntity<byte[]> exportUserTemplate() {
        return userService.exportUserTemplate();
    }
}

