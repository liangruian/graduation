package com.lra.shoporder;

import com.lra.common.entity.User;
import com.lra.common.utils.JsonResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@ComponentScan(basePackages = {"com.lra.**"})
@MapperScan("com.lra.**.mapper")
public class ShopOrderApplication {


    @RequiresAuthentication
    @GetMapping("/ss")
    public JsonResult ss(){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        return JsonResult.success(user);
    }

    public static void main(String[] args) {
        SpringApplication.run(ShopOrderApplication.class, args);
    }

}
