package com.wingsiwoo.www.entity.bo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author WingsiWoo
 * @date 2021/11/26
 */
@Data
public class CollegeBo {
    /**
     * 学院名称
     */
    @NotEmpty(message = "学院名称不能为空")
    private String name;

    /**
     * 学院简介
     */
    private String introduction;
}
