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
@TableName(value = "clazz")
public class Clazz {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 专业id
     */
    @TableField(value = "spec_id")
    private Integer specId;

    /**
     * 年级id
     */
    @TableField(value = "year_id")
    private Integer yearId;

    /**
     * 班主任id
     */
    @TableField(value = "teacher_id")
    private Integer teacherId;

    /**
     * 班级序号
     */
    @TableField(value = "clazz_index")
    private Integer clazzIndex;

}
