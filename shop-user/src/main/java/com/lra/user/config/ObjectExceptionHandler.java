package com.lra.user.config;

import com.lra.common.utils.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description
 * @Author Anglar
 * @Date 2019/10/21 14:31
 * @Version V1.0
 **/
@ControllerAdvice
public class ObjectExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ObjectExceptionHandler.class);

    /**
     * @Author: gmy
     * @Description: 系统异常捕获处理
     * @Date: 16:07 2018/5/30
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public JsonResult javaExceptionHandler(Exception ex) {//APIResponse是项目中对外统一的出口封装，可以根据自身项目的需求做相应更改
        logger.error("捕获到Exception异常",ex);
        return JsonResult.fali(ex.getMessage());
    }
}
