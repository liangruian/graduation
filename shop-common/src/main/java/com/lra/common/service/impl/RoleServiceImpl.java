package com.lra.common.service.impl;

import com.lra.common.entity.Role;
import com.lra.common.mapper.RoleMapper;
import com.lra.common.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Anglar
 * @since 2019-11-12
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    /**
     * 获取用户的角色
     * @param uid
     * @return
     */
    @Override
    public Set<String> findRoleNameByUserId(String uid) {
        List<String> roleNames = baseMapper.findRoleNameByUserId(uid);
        Set<String> collect = roleNames.stream().collect(Collectors.toSet());
        return collect;
    }
}
