package com.zws.client.config;

import com.zws.core.annotation.EnableClientCore;
import com.zws.core.authorize.AuthorizeConfigManager;
import com.zws.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/11/5
 */
@Configuration
@EnableResourceServer
@EnableClientCore
public class ResourceSecurityConfig extends ResourceServerConfigurerAdapter {


    @Autowired
    private ResourceServerTokenServices resourceServerTokenServices;
    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private OAuth2ClientAuthenticationProcessingFilter oAuth2ClientAuthenticationProcessingFilter;
    @Autowired
    private AuthorizeConfigManager authorizeConfigManager;


    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        if(resourceServerTokenServices!=null) {
            resources.tokenServices(resourceServerTokenServices);
        }
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.addFilterAfter(oAuth2ClientAuthenticationProcessingFilter
                    ,AbstractPreAuthenticatedProcessingFilter.class);
        authorizeConfigManager.config(http.authorizeRequests());
    }
}
