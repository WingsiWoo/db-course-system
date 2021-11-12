package com.wingsiwoo.www.entity.bo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author WingsiWoo
 * @date 2021/11/11
 */
@Data
public class StudentGradeExcelBo {

    @ExcelProperty("学号")
    private String account;

    @ExcelProperty("姓名")
    private String name;

    @ExcelProperty("成绩")
    private Float grade;
}
