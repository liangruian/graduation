package com.lra.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lra.common.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Anglar
 * @since 2019-11-12
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<String> findRoleNameByUserId(@Param("uid") String uid);

}
