package com.wingsiwoo.www.entity.bo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author WingsiWoo
 * @date 2021/11/11
 */
@Data
public class CoursePageBo {
    /**
     * 课程id
     */
    private Integer id;

    /**
     * 课程名称
     */
    private String name;

    /**
     * 任教老师姓名
     */
    private String teacherName;

    /**
     * 学分
     */
    private Float credit;

    /**
     * 上课时间
     */
    private String time;

    /**
     * 上课地点
     */
    private String address;

    /**
     * 选课开始时间
     */
    private LocalDateTime selectStart;

    /**
     * 选课结束时间
     */
    private LocalDateTime selectEnd;
}
