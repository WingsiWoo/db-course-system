package com.wingsiwoo.www.entity.bo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.wingsiwoo.www.constant.SexConstant;
import com.wingsiwoo.www.entity.po.User;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

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

    public static List<UserExcelBo> transformToUserExcelBo(List<User> users) {
        return users.stream().map(user -> {
            UserExcelBo userExcelBo = new UserExcelBo();
            userExcelBo.setAccount(user.getAccount());
            userExcelBo.setName(user.getName());
            userExcelBo.setSex(SexConstant.transform(user.getSex()));
            userExcelBo.setPhone(user.getPhone());
            return userExcelBo;
        }).collect(Collectors.toList());
    }
}
