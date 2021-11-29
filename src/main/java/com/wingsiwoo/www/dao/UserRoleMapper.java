package com.wingsiwoo.www.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wingsiwoo.www.entity.po.UserRole;
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
public interface UserRoleMapper extends BaseMapper<UserRole> {

    /**
     * 根据用户id查找
     *
     * @param userId 用户id
     * @return 角色id
     */
    default UserRole selectByUserId(Integer userId) {
        QueryWrapper<UserRole> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .select(UserRole::getRoleId)
                .eq(UserRole::getUserId, userId);
        return selectOne(wrapper);
    }

    /**
     * 根据角色id查找
     *
     * @param roleId 角色id
     * @return 用户id集合
     */
    default List<UserRole> selectByRoleId(Integer roleId) {
        QueryWrapper<UserRole> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .select(UserRole::getUserId)
                .eq(UserRole::getRoleId, roleId);
        return selectList(wrapper);
    }

    /**
     * 查询用户-角色相关信息
     *
     * @param userIds 用户id
     * @return 用户-角色
     */
    default List<UserRole> selectBatchByUserId(List<Integer> userIds) {
        QueryWrapper<UserRole> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .in(UserRole::getUserId, userIds);
        return selectList(wrapper);
    }
}
