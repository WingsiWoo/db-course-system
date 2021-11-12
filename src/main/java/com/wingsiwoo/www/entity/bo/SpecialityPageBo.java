package com.wingsiwoo.www.entity.bo;

import lombok.Data;

/**
 * @author WingsiWoo
 * @date 2021/11/12
 */
@Data
public class SpecialityPageBo {
    private Integer id;

    private String name;

    /**
     * 专业简介
     */
    private String introduction;

    /**
     * 学院名称
     */
    private String collegeName;
}
