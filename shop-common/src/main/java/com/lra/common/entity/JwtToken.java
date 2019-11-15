package com.lra.common.entity;

import org.apache.shiro.authc.AuthenticationToken;

public class JwtToken implements AuthenticationToken {

//    /**
//     * token的key
//     */
//    public static String ACCESS_TOKEN = "Access-Token";

    private static final long serialVersionUID = 1L;
    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
