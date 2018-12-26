package com.zws.core.support;

import org.springframework.security.core.AuthenticationException;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/10/18
 */
public class SecurityException extends AuthenticationException {

    public SecurityException(String msg, Throwable t) {
        super(msg, t);
    }

    public SecurityException(String msg) {
        super(msg);
    }

    public SecurityException(SecurityEnum errorEnum) {
        super(JsonUtils.writeValueAsString(new SimpleResponse(errorEnum)));
    }
}
