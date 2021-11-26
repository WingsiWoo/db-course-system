package com.wingsiwoo.www.entity.bo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.wingsiwoo.www.constant.SexConstant;
import com.wingsiwoo.www.entity.po.User;
import lombok.Data;

import static com.wingsiwoo.www.constant.RoleConstant.STUDENT;

/**
 * @author WingsiWoo
 * @date 2021/11/26
 */
@Data
public class ShowUserBo {
    private String account;

    private String name;

    private String sex;

    private String phone;

    private String role;

    public static ShowUserBo userTransferToBo(User user, String role) {
        ShowUserBo showUserBo = new ShowUserBo();
        showUserBo.setAccount(user.getAccount());
        showUserBo.setName(user.getName());
        showUserBo.setSex(SexConstant.transform(user.getSex()));
        showUserBo.setPhone(user.getPhone());
        showUserBo.setRole(role);
        return showUserBo;
    }
}
