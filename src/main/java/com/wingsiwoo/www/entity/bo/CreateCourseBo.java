package com.wingsiwoo.www.entity.bo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author WingsiWoo
 * @date 2021/11/12
 */
@Data
public class CreateCourseBo {
    /**
     * 课程名称
     */
    @NotEmpty(message = "课程名称不可为空")
    private String name;

    /**
     * 选课开始时间
     */
    @NotNull(message = "选课开始时间不可为空")
    private LocalDateTime selectStart;

    /**
     * 选课结束时间
     */
    @NotNull(message = "选课结束时间不可为空")
    private LocalDateTime selectEnd;

    /**
     * 任教老师id
     */
    @NotNull(message = "教师id不可为空")
    private Integer teacherId;

    /**
     * 学分
     */
    @NotNull(message = "学分不可为空")
    private Float credit;

    /**
     * 上课时间
     */
    @NotEmpty(message = "上课时间不可为空")
    private String time;

    /**
     * 教学楼号
     */
    @NotNull(message = "教学楼号不可为空")
    private Integer building;

    /**
     * 课室号
     */
    @NotNull(message = "课室号不可为空")
    private Integer num;
}
