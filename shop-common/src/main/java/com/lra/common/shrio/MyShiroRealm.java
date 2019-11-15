package com.lra.common.shrio;

import com.lra.common.entity.JwtToken;
import com.lra.common.entity.User;
import com.lra.common.service.IRoleService;
import com.lra.common.service.IUserService;
import com.lra.common.utils.RedisUtil;
import com.lra.common.utils.TokenUtils;
import com.lra.common.value.Audience;
import io.jsonwebtoken.Claims;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * @Description
 * @Author Anglar
 * @Date 2019/11/12 10:57
 * @Version V1.0
 **/
public class MyShiroRealm extends AuthorizingRealm {

    private static org.slf4j.Logger log = LoggerFactory.getLogger(MyShiroRealm.class);

    @Autowired
    private IRoleService roleService;
    @Autowired
    private IUserService userService;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    private Audience audience;
    /**
     * 必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 功能： 获取用户权限信息，包括角色以及权限。只有当触发检测用户权限时才会调用此方法，例如checkRole,checkPermission
     *
     * @param principals token
     * @return AuthorizationInfo 权限信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.info("————权限认证 [ roles、permissions]————");
        User sysUser = null;
        String username = null;
        if (principals != null) {
            sysUser = (User) principals.getPrimaryPrincipal();
            username = sysUser.getName();
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        // 设置用户拥有的角色集合，比如“admin,test”
        Set<String> roleSet = roleService.findRoleNameByUserId(sysUser.getId());
        info.setRoles(roleSet);

        // 设置用户拥有的权限集合，比如“sys:role:add,sys:user:add”
        Set<String> permissionSet = userService.findPermissionsByUserId(sysUser.getId());
        info.addStringPermissions(permissionSet);
        return info;
    }

    /**
     * 功能： 用来进行身份认证，也就是说验证用户输入的账号和密码是否正确，获取身份验证信息，错误抛出异常
     *
     * @param auth 用户身份信息 token
     * @return 返回封装了用户信息的 AuthenticationInfo 实例
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) {
        String token = (String) auth.getCredentials();
        if (token == null) {
            log.info("————————身份认证失败——————————IP地址:  ");
            throw new AuthenticationException("token为空!");
        }
        // 校验token有效性
        User loginUser = this.checkUserTokenIsEffect(token);
        return new SimpleAuthenticationInfo(loginUser, token, getName());
    }

    /**
     * 校验token的有效性
     *
     * @param token
     */
    public User checkUserTokenIsEffect(String token) throws AuthenticationException {
        // 执行认证
        if (token == null) {
            throw new AuthenticationException("无token，请重新登录");
        }
        // 获取 token 中的 user id
        String userId;
        try {
            Claims claims = TokenUtils.parseJWT(token, audience.getBase64Secret());
            userId = (String) claims.get("userId");
        } catch (Exception j) {
            throw new AuthenticationException("身份验证失败,请重新登陆！");
        }
        User user = userService.getById(userId);
        if (user == null) {
            throw new AuthenticationException("用户不存在，请重新登录");
        }
        return user;
    }


}
