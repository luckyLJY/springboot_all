package com.kk.service.impl;

import com.kk.entity.Role;
import com.kk.service.IRoleService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * role服务实现
 *
 * @author kkmystery
 * @version 1.0 2021/7/8
 * @since 1.0.0
 */
@Service
public class RoleServiceImpl implements IRoleService {
    private static HashMap<Long, List<Role>> map;

    {
        map = new HashMap<>(3);
        Role admin = new Role(1L, "admin");
        Role staff = new Role(1L, "staff");
        map.put(1L, Arrays.asList(admin, staff));
        map.put(2L, Collections.singletonList(admin));
        map.put(3L, Collections.singletonList(staff));
    }

    @Override
    public List<Role> queryByUserId(Long userId) {
        return map.get(userId);
    }
}
