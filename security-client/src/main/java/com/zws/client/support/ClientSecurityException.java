package com.zws.client.support;

import com.zws.core.support.SecurityEnum;
import com.zws.core.support.SecurityException;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/9/29
 */
public class ClientSecurityException extends SecurityException {



    public ClientSecurityException(String msg, Throwable t) {
        super(msg, t);
    }

    public ClientSecurityException(String msg) {
        super(msg);
    }

    public ClientSecurityException(SecurityEnum errorEnum) {
        super(errorEnum);
    }





}
