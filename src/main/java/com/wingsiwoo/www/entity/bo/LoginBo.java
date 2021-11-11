package com.wingsiwoo.www.entity.bo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author WingsiWoo
 * @date 2021/11/11
 */
@Data
public class LoginBo {
    /**
     * 登录账户
     */
    @NotEmpty(message = "登录账号不可为空")
    private String account;

    /**
     * 密码
     */
    @NotEmpty(message = "密码不可为空")
    private String password;
}
