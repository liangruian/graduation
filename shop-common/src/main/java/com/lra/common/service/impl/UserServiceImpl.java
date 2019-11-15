package com.lra.common.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lra.common.dto.UserRegiterDto;
import com.lra.common.entity.User;
import com.lra.common.mapper.UserMapper;
import com.lra.common.service.IUserService;
import com.lra.common.utils.JsonResult;
import com.lra.common.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Anglar
 * @since 2019-10-17
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {


    @Autowired
    private RedisUtil redisUtil;

    @Value("${user.registerString}")
    private String registercodeprekey;

    @Override
    @Transactional
    public JsonResult userRegiter(UserRegiterDto userRegiterDto) {
        User user1 = findUserByPhone(userRegiterDto.getPhone());
        if(user1 != null){
            return JsonResult.fali("您已经注册~");
        }
        String vcode = (String) redisUtil.get(registercodeprekey + userRegiterDto.getPhone());
        if(!userRegiterDto.getVerificationCode().equals(vcode)){
            return JsonResult.fali("验证码不正确~");
        }
        User user = new User();
        BeanUtil.copyProperties(userRegiterDto, user);
        user.setPassword(new Digester(DigestAlgorithm.MD5).digestHex(user.getPassword()));
        if(save(user)){
            redisUtil.del(registercodeprekey + userRegiterDto.getPhone());
            return JsonResult.success("注册成功~");
        }
        return JsonResult.success("系统发生异常~");
    }

    @Override
    public User findUserByPhone(String phone) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(User::getPhone,phone);
        User user = getOne(wrapper);
        return user;
    }

    public User findByName(String name){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(User::getName,name);
        User user = getOne(wrapper);
        return user;
    }

    @Override
    public Set<String> findPermissionsByUserId(String id) {
        return baseMapper.findPermissionsByUserId(id).stream().collect(Collectors.toSet());
    }
}
