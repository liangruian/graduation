package com.lra.common.enums;

import lombok.AllArgsConstructor;

/**
 * @Description
 * @Author Anglar
 * @Date 2019/10/18 10:53
 * @Version V1.0
 **/
@AllArgsConstructor
public enum ApiCode {

    SUCCESS(200),
    FAIL(500);

    private int code;

    public int getCode() {
        return code;
    }
}
