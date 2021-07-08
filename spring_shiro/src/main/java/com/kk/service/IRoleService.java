package com.kk.service;

import com.kk.entity.Role;

import java.util.List;

/**
 * 角色服务接口
 *
 * @author kkmystery
 * @version 1.0 2021/7/8
 * @since 1.0.0
 */
public interface IRoleService {

    /**
     * 通过用户id查询
     *
     * @param userId 用户id
     * @return role列表
     */
    List<Role> queryByUserId(Long userId);
}
