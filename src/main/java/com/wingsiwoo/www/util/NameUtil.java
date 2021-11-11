package com.wingsiwoo.www.util;

/**
 * @author WingsiWoo
 * @date 2021/11/11
 */
public class NameUtil {
    /**
     * 拼接班级完整名称
     *
     * @param year        年级
     * @param collegeName 学院名
     * @param specName    专业名
     * @param index       班级序号
     * @return xx级xx学院xx专业(x)班
     */
    public static String getClazzName(Integer year, String collegeName, String specName, Integer index) {
        return year + collegeName + specName + "专业(" + index + ")";
    }
}
