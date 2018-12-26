package com.zws.core.social.qq.config;

import com.zws.core.properties.QQProperties;
import com.zws.core.properties.SecurityProperties;
import com.zws.core.social.ConnectProviderView;
import com.zws.core.social.CustomerSocialConfigurer;
import com.zws.core.social.SocialConfig;
import com.zws.core.social.qq.connect.QQConnectionFactory;
import com.zws.core.support.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.web.servlet.View;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/10/8
 */
@Configuration
@ConditionalOnBean(SocialConfig.class)
@ConditionalOnProperty(prefix = SecurityConstants.DEFAULT_PROJECT_PREFIX+".social.qq",name = "app-id")
public class QQAutoConfig extends SocialAutoConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        QQProperties qqProperties = securityProperties.getSocial().getQq();
        return new QQConnectionFactory(qqProperties.getProviderId(),qqProperties.getAppId(),qqProperties.getAppSecret());
    }

    @Bean({"connect/qqConnect", "connect/qqConnected"})
    @ConditionalOnMissingBean(name = "qQConnectProviderView")
    public View qQConnectProviderView(){
        return new ConnectProviderView();
    }
}
