package com.wingsiwoo.www.constant;

/**
 * @author WingsiWoo
 * @date 2021/11/11
 */
public class RoleConstant {
    public static final Integer ROLE_STUDENT = 1;
    public static final Integer ROLE_TEACHER = 2;

    public static final String STUDENT = "学生";
    public static final String TEACHER = "教师";

    public static String transform(Integer roleId) {
        switch (roleId) {
            case 1:
                return STUDENT;
            case 2:
                return TEACHER;
            default:
                throw new IllegalArgumentException("参数不合法");
        }
    }
}
