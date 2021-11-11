package com.wingsiwoo.www.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wingsiwoo.www.entity.bo.ImportUserExcelBo;
import com.wingsiwoo.www.entity.bo.LoginBo;
import com.wingsiwoo.www.entity.bo.LoginResultBo;
import com.wingsiwoo.www.entity.po.User;
import org.springframework.http.ResponseEntity;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author WingsiWoo
 * @since 2021-11-11
 */
public interface UserService extends IService<User> {
    /**
     * 用户登录
     *
     * @param loginBo 登录信息
     * @return 返回信息
     */
    LoginResultBo login(LoginBo loginBo);

    /**
     * 导入用户信息
     * @param excelBo 文件+角色id
     * @return 导入结果
     */
    boolean importUserInfo(ImportUserExcelBo excelBo);

    /**
     * 导出用户模板
     * @return 模板文件
     */
    ResponseEntity<byte[]> exportUserTemplate();
}
