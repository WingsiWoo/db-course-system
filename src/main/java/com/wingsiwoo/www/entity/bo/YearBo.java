package com.wingsiwoo.www.entity.bo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author WingsiWoo
 * @date 2021/11/27
 */
@Data
public class YearBo {
    /**
     * 年级id
     */
    @NotNull(message = "年级id不可为空")
    private Integer id;

    /**
     * 是否为已毕业年级，0-否，1-是
     */
    @NotNull(message = "毕业标识不可为空")
    private Byte graduate;
}
