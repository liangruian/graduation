package com.lra.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lra.common.dto.UserRegiterDto;
import com.lra.common.entity.User;
import com.lra.common.utils.JsonResult;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Anglar
 * @since 2019-10-17
 */
public interface IUserService extends IService<User> {

    JsonResult userRegiter(UserRegiterDto userRegiterDto);

    User findUserByPhone(String phone);

    User findByName(String name);
}
