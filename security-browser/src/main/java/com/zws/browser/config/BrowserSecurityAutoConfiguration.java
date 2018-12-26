package com.zws.browser.config;


import com.zws.core.annotation.EnableAuthenticationCore;
import com.zws.core.authentication.LoginSecurityConfig;
import com.zws.core.authentication.sms.SmsCodeAuthenticationSecurityConfig;
import com.zws.core.authorize.AuthorizeConfigManager;
import com.zws.core.authorize.AuthorizeConfigProvider;
import com.zws.core.validate.ValidateSecurityConfig;
import com.zws.core.support.SecurityConstants;
import com.zws.core.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.session.ExpiringSession;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;
import java.util.concurrent.ExecutionException;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/9/28
 */
@Configuration
@EnableAuthenticationCore
public class BrowserSecurityAutoConfiguration extends WebSecurityConfigurerAdapter {



    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private ValidateSecurityConfig validateSecurityConfig;
    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;
    @Autowired
    private UserDetailsService userDetailsServiceImpl;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private SpringSocialConfigurer customerSocialConfigurer;
    @Autowired
    private InvalidSessionStrategy invalidSessionStrategy;
    @Autowired
    private SessionInformationExpiredStrategy expiredStrategy;
    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;
    @Autowired
    private LoginSecurityConfig loginSecurityConfig;

    @Autowired(required = false)
    private SessionRegistry sessionRegistry;

    @Autowired
    private AuthorizeConfigManager authorizeConfigManager;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        loginSecurityConfig.configure(http);
         http.apply(validateSecurityConfig)
                .and()
             .apply(smsCodeAuthenticationSecurityConfig)
                .and()
             .apply(customerSocialConfigurer)
                 .and()
             .rememberMe()
                .tokenRepository(persistentTokenRepository(dataSource))
                .tokenValiditySeconds(securityProperties.getBrowser().getRememberMeSeconds())
                .userDetailsService(userDetailsServiceImpl)
                .and()
             .sessionManagement()
                .invalidSessionStrategy(invalidSessionStrategy)
                .maximumSessions(securityProperties.getBrowser().getSession().getMaximumSessions())
                .maxSessionsPreventsLogin(securityProperties.getBrowser().getSession().getMaxSessionsPreventsLogin())
                .expiredSessionStrategy(expiredStrategy)
                 .sessionRegistry(sessionRegistry)
                .and()
                .and()
             .logout()
                .logoutUrl(securityProperties.getBrowser().getLogoutUrl())
                .logoutSuccessHandler(logoutSuccessHandler)
                .deleteCookies(securityProperties.getBrowser().getSession().getSessionKey())
                .and()
             .csrf()
                .disable();
         authorizeConfigManager.config(http.authorizeRequests());
    }


    @Bean
    public PersistentTokenRepository persistentTokenRepository(DataSource dataSource){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }









}
