package com.zws.core.validate;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/9/30
 */
public interface ValidateCodeHandler {



    void create(ServletWebRequest servletWebRequest) ;

    void validate(ServletWebRequest servletWebRequest);
}
