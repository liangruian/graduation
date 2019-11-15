package com.lra.user.controller;


import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import com.lra.common.dto.PhoneCodeSendDto;
import com.lra.common.dto.UserRegiterDto;
import com.lra.common.entity.User;
import com.lra.common.service.IUserService;
import com.lra.common.utils.JsonResult;
import com.lra.common.utils.PhoneVCodeSendUtils;
import com.lra.common.utils.RedisUtil;
import com.lra.common.utils.TokenUtils;
import com.lra.common.value.Audience;
import com.lra.user.dto.UserLoginDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户相关接口
 * </p>
 *
 * @author Anglar
 * @since 2019-10-17
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户相关接口")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private PhoneVCodeSendUtils vCodeSendUtils;

    @Autowired
    private Audience audience;

    @ApiOperation("用户注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public JsonResult addUser(@RequestBody @Validated UserRegiterDto userRegiterDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return JsonResult.fali(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        return userService.userRegiter(userRegiterDto);

    }
    @ApiOperation("用户登陆（手机密码）")
    @PostMapping("/loginByPassword")
    public JsonResult login(@RequestBody UserLoginDto userLoginDto) {
        User userForBase = userService.findUserByPhone(userLoginDto.getPhone());
        if (userForBase == null) {
            return JsonResult.fali("登录失败,用户不存在");
        } else {
            if (!userForBase.getPassword().equals(new Digester(DigestAlgorithm.MD5).digestHex(userLoginDto.getPassword()))) {
                return JsonResult.fali("登录失败,密码错误");
            } else {
                String token = TokenUtils.createJWT(userForBase.getId(),audience.getExpiresSecond(),audience.getBase64Secret());
                return JsonResult.success("登陆成功",token);
            }
        }
    }


    @RequiresAuthentication
    @GetMapping("/ss")
    public JsonResult ss(){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        return JsonResult.success(user);
    }


    @ApiOperation("发送用户注册验证码")
    @RequestMapping(value = "/sendRegisterPhoneCode", method = RequestMethod.GET)
    public JsonResult sendRegisterPhoneCode(PhoneCodeSendDto sendDto) throws Exception {
        //送手机验证码
        boolean sendCode = vCodeSendUtils.sendCode(sendDto.getPhone());
        if (sendCode) {
            return JsonResult.success("发送成功！");
        } else {
            return JsonResult.fali("发送失败~");
        }
    }

}
