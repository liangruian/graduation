package com.lra.common.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @Description
 * @Author Anglar
 * @Date 2019/10/18 12:28
 * @Version V1.0
 **/
@Data
public class PhoneCodeSendDto {

    @NotNull(message = "手机号不能为空")
    @Pattern(regexp = "^[1](([3][0-9])|([4][5,7,9])|([5][0-9])|([6][6])|([7][3,5,6,7,8])|([8][0-9])|([9][8,9]))[0-9]{8}$",message = "请输入正确的手机号码~")
    private String phone;
}
