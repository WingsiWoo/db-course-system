package com.wingsiwoo.www.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wingsiwoo.www.entity.po.User;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author WingsiWoo
 * @since 2021-11-11
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据账户查询
     *
     * @param account 账户
     * @return 账户信息
     */
    default User selectByAccount(String account) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(StringUtils.isNotEmpty(account), User::getAccount, account);
        return selectOne(wrapper);
    }

    /**
     * 根据账户集合检查用户是否存在
     *
     * @param accounts 账户集合
     */
    default List<User> selectBatchByAccounts(List<String> accounts) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .in(CollectionUtils.isNotEmpty(accounts), User::getAccount, accounts);
        return selectList(wrapper);
    }

    /**
     * 查询班级内所有学生信息
     *
     * @param clazzId 班级id
     * @return 学生信息
     */
    default List<User> selectBatchByClazzId(Integer clazzId) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(User::getClassId, clazzId);
        return selectList(wrapper);
    }
}
