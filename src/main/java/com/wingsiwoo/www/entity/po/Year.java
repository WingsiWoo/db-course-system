package com.wingsiwoo.www.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * <p>
 *
 * </p>
 *
 * @author WingsiWoo
 * @since 2021-11-11
 */
@ToString
@EqualsAndHashCode
@Data
@TableName(value = "year")
public class Year {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 年度号
     */
    @TableField(value = "year")
    private Integer year;

    /**
     * 是否为已毕业年级，0-否，1-是
     */
    @TableField(value = "grade")
    private Boolean graduate;

}
