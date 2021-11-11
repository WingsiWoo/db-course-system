package com.wingsiwoo.www.service.impl;

import com.wingsiwoo.www.entity.po.User;
import com.wingsiwoo.www.dao.UserMapper;
import com.wingsiwoo.www.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author WingsiWoo
 * @since 2021-11-11
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
