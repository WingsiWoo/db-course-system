package com.wingsiwoo.www.entity.bo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author WingsiWoo
 * @date 2021/11/11
 */
@Data
public class UserExcelBo {
    @ExcelProperty(value = "学号/工号", index = 0)
    private String account;

    @ExcelProperty(value = "姓名", index = 1)
    private String name;

    @ExcelProperty(value = "性别-男/女", index = 2)
    private String sex;

    @ExcelProperty(value = "联系电话", index = 3)
    private String phone;
}
