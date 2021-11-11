package com.wingsiwoo.www.entity.bo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author WingsiWoo
 * @date 2021/11/11
 */
@Data
@AllArgsConstructor
public class LoginResultBo {
    /**
     * 账户
     */
    private String account;

    /**
     * 姓名
     */
    private String name;

    /**
     * 角色id
     */
    private Integer role;
}
