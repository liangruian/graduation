package com.lra.common.service;

import com.lra.common.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Anglar
 * @since 2019-11-12
 */
public interface IRoleService extends IService<Role> {

    Set<String> findRoleNameByUserId(String uid);
}
