package com.lra.common.service.impl;

import com.lra.common.entity.UserRole;
import com.lra.common.mapper.UserRoleMapper;
import com.lra.common.service.IUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户角色 服务实现类
 * </p>
 *
 * @author Anglar
 * @since 2019-11-12
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

}
