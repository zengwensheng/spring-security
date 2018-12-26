package com.zws.core.authentication;

import com.zws.core.support.SecurityConstants;
import lombok.Data;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/9/28
 */
@Data
public  class LoginSecurityConfig  {



    private AuthenticationSuccessHandler authenticationSuccessHandlerImpl;

    private AuthenticationFailureHandler authenticationFailureHandlerImpl;

    public void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage(SecurityConstants.DEFAULT_UN_AUTHENTICATION_URL)
                .loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL)
                .successHandler(authenticationSuccessHandlerImpl)
                .failureHandler(authenticationFailureHandlerImpl);
    }
}
