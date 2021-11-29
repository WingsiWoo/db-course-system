package com.wingsiwoo.www.entity.bo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author WingsiWoo
 * @date 2021/11/26
 */
@Data
public class SpecialityBo {
    /**
     * 专业名称
     */
    @NotEmpty(message = "专业名称不可为空")
    private String name;

    /**
     * 专业简介
     */
    private String introduction;

    /**
     * 所属学院id
     */
    @NotNull(message = "所属学院id不可为空")
    private Integer collegeId;
}
