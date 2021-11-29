package com.wingsiwoo.www.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wingsiwoo.www.entity.bo.LoginBo;
import com.wingsiwoo.www.entity.bo.LoginResultBo;
import com.wingsiwoo.www.entity.bo.ShowUserBo;
import com.wingsiwoo.www.entity.bo.UpdatePasswordBo;
import com.wingsiwoo.www.entity.po.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

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
     *
     * @param file   文件
     * @param roleId 角色id
     * @return 导入结果
     */
    boolean importUserInfo(MultipartFile file, Integer roleId);

    /**
     * 导出用户模板
     *
     * @return 模板文件
     */
    ResponseEntity<byte[]> exportUserTemplate();

    /**
     * 修改密码
     *
     * @param updatePasswordBo updatePasswordBo
     * @return 修改结果
     */
    boolean updatePassword(UpdatePasswordBo updatePasswordBo);

    /**
     * 分页查询用户信息
     *
     * @param roleId 角色id
     * @return 用户信息
     */
    Page<ShowUserBo> showAllUserInPage(Integer roleId);
}
