package com.wingsiwoo.www.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

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
@TableName(value = "course")
public class Course {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 课程名称
     */
    @TableField(value = "name")
    private String name;

    /**
     * 选课开始时间
     */
    @TableField(value = "select_start")
    private LocalDateTime selectStart;

    /**
     * 选课结束时间
     */
    @TableField(value = "select_end")
    private LocalDateTime selectEnd;

    /**
     * 任教老师id
     */
    @TableField(value = "teacher_id")
    private Integer teacherId;

    /**
     * 学分
     */
    @TableField(value = "credit")
    private Float credit;

}
