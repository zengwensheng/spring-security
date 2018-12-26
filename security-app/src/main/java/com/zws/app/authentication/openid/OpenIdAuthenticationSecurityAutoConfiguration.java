package com.zws.app.authentication.openid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.social.connect.UsersConnectionRepository;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/10/17
 */
@Configuration
public class OpenIdAuthenticationSecurityAutoConfiguration extends SecurityConfigurerAdapter<DefaultSecurityFilterChain,HttpSecurity> {

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandlerImpl;
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandlerImpl;
    @Autowired
    private UserDetailsChecker userDetailsChecker;
    @Autowired
    private UsersConnectionRepository usersConnectionRepository;
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        OpenIdAuthenticationFilter openIdAuthenticationFilter = new OpenIdAuthenticationFilter();
        openIdAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        openIdAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandlerImpl);
        openIdAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandlerImpl);

        OpenIdAuthenticationProvider openIdAuthenticationProvider = new OpenIdAuthenticationProvider();
        openIdAuthenticationProvider.setUserDetailsChecker(userDetailsChecker);
        openIdAuthenticationProvider.setUserDetailsService(userDetailsService);
        openIdAuthenticationProvider.setUsersConnectionRepository(usersConnectionRepository);

        http.authenticationProvider(openIdAuthenticationProvider).addFilterAfter(openIdAuthenticationFilter,UsernamePasswordAuthenticationFilter.class);

    }
}
