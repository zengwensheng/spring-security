package com.zws.browser.config;

import com.zws.browser.authentication.AuthenticationFailureHandlerImpl;
import com.zws.browser.authentication.AuthenticationSuccessHandlerImpl;
import com.zws.browser.authentication.BrowserSecurityController;
import com.zws.browser.authorize.BrowserAuthorizeConfigProvider;
import com.zws.browser.logout.BrowserLogoutSuccessHandler;
import com.zws.browser.session.ExpiredSessionStrategy;
import com.zws.browser.session.InvalidSessionStrategy;
import com.zws.browser.validate.SessionValidateCodeRepository;
import com.zws.core.authorize.AuthorizeConfigProvider;
import com.zws.core.properties.SecurityProperties;
import com.zws.core.validate.ValidateCodeRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import javax.servlet.http.HttpServletRequest;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/9/29
 */
@Configuration
@Import(BrowserSecurityController.class)
public class BrowserSecurityBeanAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(name = "authenticationSuccessHandlerImpl")
    public AuthenticationSuccessHandler authenticationSuccessHandlerImpl(SecurityProperties securityProperties) {
        AuthenticationSuccessHandlerImpl authenticationSuccessHandlerImpl = new AuthenticationSuccessHandlerImpl();
        authenticationSuccessHandlerImpl.setSecurityProperties(securityProperties);
        return authenticationSuccessHandlerImpl;
    }

    @Bean
    @ConditionalOnMissingBean(name = "authenticationFailureHandlerImpl")
    public AuthenticationFailureHandler authenticationFailureHandlerImpl(SecurityProperties securityProperties) {
        AuthenticationFailureHandlerImpl authenticationFailureHandlerImpl = new AuthenticationFailureHandlerImpl(securityProperties.getBrowser().getLogErrorUrl());
        authenticationFailureHandlerImpl.setSecurityProperties(securityProperties);
        authenticationFailureHandlerImpl.setDefaultFailureUrl(securityProperties.getBrowser().getFailureUrl());
        return authenticationFailureHandlerImpl;
    }

    @Bean
    @ConditionalOnMissingBean(InvalidSessionStrategy.class)
    public InvalidSessionStrategy invalidSessionStrategy(SecurityProperties securityProperties){
        return new InvalidSessionStrategy(securityProperties.getBrowser().getSession().getSessionInvalidUrl(),securityProperties.getBrowser().getSession().getSessionKey());
    }

    @Bean
    @ConditionalOnMissingBean(SessionInformationExpiredStrategy.class)
    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy(SecurityProperties securityProperties){
        return new ExpiredSessionStrategy(securityProperties.getBrowser().getSession().getSessionInvalidUrl(),securityProperties.getBrowser().getSession().getSessionKey());
    }

    @Bean
    @ConditionalOnMissingBean(ValidateCodeRepository.class)
    public SessionValidateCodeRepository sessionValidateCodeRepository(HttpServletRequest request){
         SessionValidateCodeRepository sessionValidateCodeRepository =new SessionValidateCodeRepository();
         sessionValidateCodeRepository.setRequest(request);
         return sessionValidateCodeRepository;
    }

    @Bean
    @ConditionalOnMissingBean(LogoutSuccessHandler.class)
    public LogoutSuccessHandler logoutSuccessHandler(SecurityProperties securityProperties){
        BrowserLogoutSuccessHandler browserLogoutSuccessionHandler =new BrowserLogoutSuccessHandler();
        browserLogoutSuccessionHandler.setSecurityProperties(securityProperties);
        return browserLogoutSuccessionHandler;
    }


    @Bean
    public AuthorizeConfigProvider authorizeConfigProvider(SecurityProperties securityProperties){
        BrowserAuthorizeConfigProvider browserAuthorizeConfigProvider =new BrowserAuthorizeConfigProvider();
        browserAuthorizeConfigProvider.setSecurityProperties(securityProperties);
        return browserAuthorizeConfigProvider;
    }


}
