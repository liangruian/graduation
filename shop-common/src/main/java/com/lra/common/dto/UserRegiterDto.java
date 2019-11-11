package com.lra.common.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @Description
 * @Author Anglar
 * @Date 2019/10/18 11:12
 * @Version V1.0
 **/
@Data
public class UserRegiterDto {

    @NotNull(message = "用户名不能为空")
    private String name;

    @NotNull(message = "密码不能为空")
    @Size(min = 6,max = 16,message = "密码长度为6-16位~")
    private String password;

    @NotNull(message = "手机号不能为空")
    @Pattern(regexp = "^[1](([3][0-9])|([4][5,7,9])|([5][0-9])|([6][6])|([7][3,5,6,7,8])|([8][0-9])|([9][8,9]))[0-9]{8}$",message = "请输入正确的手机号码~")
    private String phone;

    private String hearImg;

    @NotNull(message = "验证码不能为空")
    private String verificationCode;


}
