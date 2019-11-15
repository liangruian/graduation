package com.lra.user.dto;

import lombok.Data;

/**
 * @Description
 * @Author Anglar
 * @Date 2019/10/21 16:48
 * @Version V1.0
 **/
@Data
public class UserLoginDto {

    private String phone;

    private String password;
}
