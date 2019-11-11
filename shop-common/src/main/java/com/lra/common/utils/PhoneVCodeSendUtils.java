package com.lra.common.utils;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.NumberUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author Anglar
 * @Date 2019/10/18 14:27
 * @Version V1.0
 **/
@Component
public class PhoneVCodeSendUtils {

    @Autowired
    private RedisUtil redisUtil;

    @Value("${user.registerString}")
    private String registercodeprekey;

    @Value("${user.codeTimeout}")
    private Long codeTimeout;

    @Autowired
    private SendSmsUtils sendSmsUtils;

    public boolean sendCode(String phoneNumber) throws Exception {
        if (StringUtils.isNotBlank((String) redisUtil.get(registercodeprekey + phoneNumber))) {
            return false;
        }
        //发送验证码
        int[] ints = NumberUtil.generateRandomNumber(0, 9, 6);
        String code = ArrayUtil.join(ints, "");
        try {
            sendSmsUtils.sendSms(phoneNumber, code);
        } catch (Exception e) {
            throw new Exception("发送手机验证码失败");
        }
        redisUtil.set(registercodeprekey + phoneNumber, code, codeTimeout);
        return true;
    }

}
