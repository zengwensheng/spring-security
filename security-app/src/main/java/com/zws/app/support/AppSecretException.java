package com.zws.app.support;

import com.zws.core.support.JsonUtils;
import com.zws.core.support.SecurityEnum;
import com.zws.core.support.SecurityException;
import com.zws.core.support.SimpleResponse;
import org.springframework.security.core.AuthenticationException;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/9/29
 */
public class AppSecretException extends SecurityException {



    public AppSecretException(String msg, Throwable t) {
        super(msg, t);
    }

    public AppSecretException(String msg) {
        super(msg);
    }

    public AppSecretException(SecurityEnum errorEnum) {
        super(errorEnum);
    }





}
