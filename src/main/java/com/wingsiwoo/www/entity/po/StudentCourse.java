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
@TableName(value = "student_course")
public class StudentCourse {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "student_id")
    private Integer studentId;

    @TableField(value = "course_id")
    private Integer courseId;

    @TableField(value = "grade")
    private Float grade;

}
