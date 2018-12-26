package com.zws.app.social;

import com.zws.core.properties.SecurityProperties;
import com.zws.core.social.support.SocialAuthenticationFilterPostProcessor;
import lombok.Data;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SocialAuthenticationFilter;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/10/17
 */
@Data
public class AppSocialAuthenticationFilterPostProcessor  implements SocialAuthenticationFilterPostProcessor {


    private AuthenticationSuccessHandler authenticationSuccessHandler;

    private SecurityProperties securityProperties;

    @Override
    public void process(SocialAuthenticationFilter socialAuthenticationFilter) {
        socialAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        socialAuthenticationFilter.setSignupUrl(securityProperties.getApp().getSignUpUrl());
        socialAuthenticationFilter.setDefaultFailureUrl(securityProperties.getApp().getSignInUrl());
    }
}
