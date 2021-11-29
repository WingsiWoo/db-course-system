package com.wingsiwoo.www.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author WingsiWoo
 * @date 2021/11/27
 */
@ToString
@EqualsAndHashCode
@Data
@TableName(value = "address")
public class Address {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 楼号
     */
    @TableField(value = "building")
    private Integer building;

    /**
     * 课室序号
     */
    @TableField(value = "num")
    private Integer num;
}
