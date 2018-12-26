package com.zws.core.token.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/10/25
 */
public class TokenAuthenticationException extends AuthenticationException {

    public TokenAuthenticationException(String msg) {
        super(msg);
    }
}
