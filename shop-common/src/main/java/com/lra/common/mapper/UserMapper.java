package com.lra.common.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lra.common.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author Anglar
 * @since 2019-10-17
 */
public interface UserMapper extends BaseMapper<User> {

    List<String> findPermissionsByUserId(@Param("uid") String uid);

}
