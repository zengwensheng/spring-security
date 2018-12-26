package com.zws.browser.authorize;

import com.zws.core.authorize.AuthorizeConfigProvider;
import com.zws.core.properties.SecurityProperties;
import com.zws.core.support.SecurityConstants;
import lombok.Data;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/11/7
 */

@Data
public class BrowserAuthorizeConfigProvider implements AuthorizeConfigProvider {

    private SecurityProperties securityProperties;

    @Override
    public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry) {
        registry .antMatchers(securityProperties.getBrowser().getLoginPage()
                ,SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL
                ,SecurityConstants.DEFAULT_UN_AUTHENTICATION_URL
                ,securityProperties.getBrowser().getFailureUrl()
                ,SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_SMS
                ,SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX+"/*"
                ,securityProperties.getBrowser().getSignUpUrl()
                ,securityProperties.getBrowser().getSignInUrl()
                ,securityProperties.getBrowser().getSignOutUrl()
                ,securityProperties.getBrowser().getLogErrorUrl()
                ,SecurityConstants.DEFAULT_SOCIAL_USER_INFO_URL
                ,securityProperties.getBrowser().getSession().getSessionInvalidUrl())
                .permitAll();
        return false;
    }
}
