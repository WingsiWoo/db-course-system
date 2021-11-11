package com.wingsiwoo.www.entity.bo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author WingsiWoo
 * @date 2021/11/11
 */
@Data
public class UpdateGradeBo {
    @NotNull(message = "用户id不可为空")
    private Integer userId;

    @NotEmpty(message = "学号不可为空")
    private String account;

    @NotNull(message = "课程id不可为空")
    private Integer courseId;

    @NotNull(message = "成绩不可为空")
    private Float grade;
}
