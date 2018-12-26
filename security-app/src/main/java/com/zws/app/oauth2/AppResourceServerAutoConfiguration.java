package com.zws.app.oauth2;

import com.zws.app.authentication.openid.OpenIdAuthenticationSecurityAutoConfiguration;
import com.zws.core.authentication.LoginSecurityConfig;
import com.zws.core.authentication.sms.SmsCodeAuthenticationSecurityConfig;
import com.zws.core.authorize.AuthorizeConfigManager;
import com.zws.core.properties.SecurityProperties;
import com.zws.core.validate.ValidateSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.social.security.SpringSocialConfigurer;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/10/17
 */
@Configuration
@EnableResourceServer
public class AppResourceServerAutoConfiguration extends ResourceServerConfigurerAdapter {


    @Autowired
    private ValidateSecurityConfig validateSecurityConfig;
    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;
    @Autowired
    private SpringSocialConfigurer customerSocialConfigurer;
    @Autowired
    private LoginSecurityConfig loginSecurityConfig;
    @Autowired
    private OpenIdAuthenticationSecurityAutoConfiguration openIdAuthenticationSecurityAutoConfiguration;
    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private ResourceServerTokenServices customTokenService;
    @Autowired
    private AuthorizeConfigManager authorizeConfigManager;



    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        if(customTokenService!=null) {
            resources.tokenServices(customTokenService);
        }
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        loginSecurityConfig.configure(http);
        http.apply(validateSecurityConfig)
                .and()
            .apply(smsCodeAuthenticationSecurityConfig)
                .and()
            .apply(customerSocialConfigurer)
                .and()
            .apply(openIdAuthenticationSecurityAutoConfiguration);
        authorizeConfigManager.config(http.authorizeRequests());



    }


}
