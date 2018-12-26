package com.zws.demo.authorize;

import com.zws.core.authorize.AuthorizeConfigProvider;
import com.zws.core.properties.SecurityProperties;
import com.zws.core.support.SecurityConstants;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/11/7
 */

@Component
public class DemoAuthorizeConfigProvider implements AuthorizeConfigProvider {


    @Override
    public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry) {
        registry .antMatchers(
                "/user/binding"
                        ,"/user/appBinding"
                        ,"/login.html"
                        ,"/v2/api-docs", "/configuration/ui", "/swagger-resources", "/configuration/security", "/swagger-ui.html", "/webjars/**","/swagger-resources/configuration/ui","/swagge‌​r-ui.html"

                     )
                .permitAll();
        return false;
    }
}
