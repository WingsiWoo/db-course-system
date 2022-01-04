package com.wingsiwoo.www.util;

/**
 * @author WingsiWoo
 * @date 2021/11/11
 */
public class NameUtil {
    /**
     * 拼接班级完整名称
     *
     * @param grade        年级
     * @param collegeName 学院名
     * @param specName    专业名
     * @param index       班级序号
     * @return xx级xx学院xx专业(x)班
     */
    public static String getClazzName(Integer grade, String collegeName, String specName, Integer index) {
        return grade + collegeName + specName + "专业(" + index + ")班";
    }

    /**
     * 拼接上课地点完整名称
     *
     * @param building 楼号
     * @param num      课室序号
     * @return 教学x号楼xxx
     */
    public static String getAddressName(Integer building, Integer num) {
        return "教学" + building + "号楼" + num;
    }
}
