package com.zws.core.social;

import com.zws.core.properties.SecurityProperties;
import com.zws.core.social.support.SocialAuthenticationFilterPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/10/8
 */
public class CustomerSocialConfigurer extends SpringSocialConfigurer {


    private SecurityProperties securityProperties;

    private SocialAuthenticationFilterPostProcessor socialAuthenticationFilterPostProcessor;

    private SessionAuthenticationStrategy sessionAuthenticationStrategy;





    public CustomerSocialConfigurer(SecurityProperties securityProperties,SocialAuthenticationFilterPostProcessor socialAuthenticationFilterPostProcessor) {
        this.securityProperties = securityProperties;
        this.socialAuthenticationFilterPostProcessor = socialAuthenticationFilterPostProcessor;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        sessionAuthenticationStrategy =  http.getSharedObject(SessionAuthenticationStrategy.class);
        super.configure(http);
    }


    @Override
    protected <T> T postProcess(T object) {
        SocialAuthenticationFilter filter = (SocialAuthenticationFilter) super.postProcess(object);
        filter.setFilterProcessesUrl(securityProperties.getSocial().getFilterProcessesUrl());
        filter.setSignupUrl(securityProperties.getBrowser().getSignUpUrl());
        filter.setDefaultFailureUrl(securityProperties.getBrowser() .getSignInUrl());
        if(sessionAuthenticationStrategy !=null) {
            filter.setSessionAuthenticationStrategy(sessionAuthenticationStrategy);
        }
        if(socialAuthenticationFilterPostProcessor!=null){
            socialAuthenticationFilterPostProcessor.process(filter);
        }
        return (T) filter;
    }


}
