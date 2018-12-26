package com.zws.core.authentication.sms;

import com.zws.core.authentication.sms.SmsCodeAuthenticationFilter;
import com.zws.core.authentication.sms.SmsCodeAuthenticationProvider;
import com.zws.core.authentication.sms.SmsCodeUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/10/8
 */
@Configuration
public class SmsCodeAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain,HttpSecurity> {

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandlerImpl;
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandlerImpl;
    @Autowired
    private SmsCodeUserDetailsService smsCodeUserDetailsService;
    @Autowired
    private UserDetailsChecker userDetailsChecker;


    @Override
    public void configure(HttpSecurity http) throws Exception {
        SmsCodeAuthenticationFilter smsCodeAuthenticationFilter = new SmsCodeAuthenticationFilter();
        smsCodeAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        smsCodeAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandlerImpl);
        smsCodeAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandlerImpl);

        SessionAuthenticationStrategy sessionAuthenticationStrategy = http
                .getSharedObject(SessionAuthenticationStrategy.class);
        if (sessionAuthenticationStrategy != null) {
            smsCodeAuthenticationFilter.setSessionAuthenticationStrategy(sessionAuthenticationStrategy);
        }

        SmsCodeAuthenticationProvider smsCodeAuthenticationProvider = new SmsCodeAuthenticationProvider();
        smsCodeAuthenticationProvider.setSmsCodeUserDetailsService(smsCodeUserDetailsService);
        smsCodeAuthenticationProvider.setUserDetailsChecker(userDetailsChecker);


        http.authenticationProvider(smsCodeAuthenticationProvider)
                .addFilterAfter(smsCodeAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    }


}
