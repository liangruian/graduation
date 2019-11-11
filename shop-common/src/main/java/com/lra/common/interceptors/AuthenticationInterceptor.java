package com.lra.common.interceptors;

import com.lra.common.annotations.PassToken;
import com.lra.common.annotations.UserLoginToken;
import com.lra.common.entity.User;
import com.lra.common.exceptions.LoginException;
import com.lra.common.service.IUserService;
import com.lra.common.utils.TokenUtils;
import com.lra.common.value.Audience;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @Description
 * @Author Anglar
 * @Date 2019/10/21 15:26
 * @Version V1.0
 **/
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    IUserService userService;

    @Autowired
    private Audience audience;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (userLoginToken.required()) {
                // 执行认证
                if (token == null) {
                    throw new RuntimeException("无token，请重新登录");
                }
                // 获取 token 中的 user id
                String userId;
                try {
                    Claims claims = TokenUtils.parseJWT(token, audience.getBase64Secret());
                    userId = (String) claims.get("userId");
                } catch (Exception j) {
                    throw new LoginException("身份验证失败,请重新登陆！");
                }
                User user = userService.getById(userId);
                if (user == null) {
                    throw new RuntimeException("用户不存在，请重新登录");
                }
                return true;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }
}
