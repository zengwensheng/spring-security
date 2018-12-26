package com.zws.core.authorize;

import com.zws.core.support.SecurityEnum;
import com.zws.core.support.SecurityException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

import java.util.List;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/11/7
 */
@Data
@Slf4j
public class AuthorizeConfigManagerImpl implements AuthorizeConfigManager {

    @Autowired
    private List<AuthorizeConfigProvider> providerList;

    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry) {
        boolean existAnyRequestConfig = false;
        String existAnyRequestConfigName = null;

        if(providerList!=null&&providerList.size()>0) {

            for (AuthorizeConfigProvider authorizeConfigProvider : providerList) {
                boolean currentIsAnyRequestConfig = authorizeConfigProvider.config(registry);
                if (existAnyRequestConfig && currentIsAnyRequestConfig) {
                    log.error("重复配置anyRequest(),{}", existAnyRequestConfigName);
                    throw new SecurityException(SecurityEnum.SYSTEM_REPEAT_CONFIG_ANYREQUEST);
                } else if (currentIsAnyRequestConfig) {
                    existAnyRequestConfig = true;
                    existAnyRequestConfigName = authorizeConfigProvider.getClass().getSimpleName();
                }
            }
        }

        if(!existAnyRequestConfig){
            registry.anyRequest().authenticated();
        }
    }
}
