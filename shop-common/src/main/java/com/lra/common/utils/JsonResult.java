package com.lra.common.utils;

import com.lra.common.enums.ApiCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @Author Anglar
 * @Date 2019/10/18 10:53
 * @Version V1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonResult {

    private ApiCode apiCode;

    private String msg;

    private Object data;

    public static JsonResult success(String msg, Object data) {
        return new JsonResult(ApiCode.SUCCESS, msg, data);
    }

    public static JsonResult success() {
        return new JsonResult(ApiCode.SUCCESS, "成功",null);
    }

    public static JsonResult success(Object data) {
        return new JsonResult(ApiCode.SUCCESS, "成功", data);
    }

    public static JsonResult success(String msg){
        return new JsonResult(ApiCode.SUCCESS, msg, null);
    }

    public static JsonResult fali(String msg,Object data){
        return new JsonResult(ApiCode.FAIL,msg,data);
    }

    public static JsonResult fali(){
        return new JsonResult(ApiCode.FAIL,"失败",null);
    }

    public static JsonResult fali(String msg){
        return new JsonResult(ApiCode.FAIL,msg,null);
    }
}
