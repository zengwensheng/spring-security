package com.zws.core.config;

import com.zws.core.authentication.LoginSecurityConfig;
import com.zws.core.authorize.AuthorizeConfigManager;
import com.zws.core.authorize.AuthorizeConfigManagerImpl;
import com.zws.core.authorize.AuthorizeConfigProvider;
import com.zws.core.properties.SecurityProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.util.List;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/9/29
 */
@Configuration
public class SecurityCoreConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public LoginSecurityConfig loginSecurityConfig(AuthenticationSuccessHandler authenticationSuccessHandlerImpl, AuthenticationFailureHandler authenticationFailureHandlerImpl){
        LoginSecurityConfig loginSecurityConfig = new LoginSecurityConfig();
        loginSecurityConfig.setAuthenticationSuccessHandlerImpl(authenticationSuccessHandlerImpl);
        loginSecurityConfig.setAuthenticationFailureHandlerImpl(authenticationFailureHandlerImpl);
        return loginSecurityConfig;
    }

    @Bean
    public UserDetailsChecker accountStatusUserDetailsChecker(){
        UserDetailsChecker accountStatusUserDetailsChecker = new AccountStatusUserDetailsChecker();
        return accountStatusUserDetailsChecker;
    }

    @Bean
    public AuthorizeConfigManager authorizeConfigManager(List<AuthorizeConfigProvider> providerList){
        AuthorizeConfigManagerImpl authorizeConfigManager = new AuthorizeConfigManagerImpl();
        authorizeConfigManager.setProviderList(providerList);
        return authorizeConfigManager;
    }

}
