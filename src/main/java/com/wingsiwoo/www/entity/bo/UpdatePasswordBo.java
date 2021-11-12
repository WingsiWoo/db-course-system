package com.wingsiwoo.www.entity.bo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author WingsiWoo
 * @date 2021/11/12
 */
@Data
public class UpdatePasswordBo {
    @NotNull(message = "用户id不可为空")
    private Integer userId;

    @NotEmpty(message = "旧密码不可为空")
    private String oldPassword;

    @NotEmpty(message = "新密码不可为空")
    private String newPassword;
}
