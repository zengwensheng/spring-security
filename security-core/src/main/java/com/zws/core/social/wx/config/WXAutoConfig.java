package com.zws.core.social.wx.config;

import com.zws.core.properties.SecurityProperties;
import com.zws.core.properties.WXProperties;
import com.zws.core.social.ConnectProviderView;
import com.zws.core.social.SocialConfig;
import com.zws.core.social.wx.connect.WXConnectionFactory;
import com.zws.core.support.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.AbstractView;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/10/8
 */
@Configuration
@ConditionalOnBean(SocialConfig.class)
@ConditionalOnProperty(prefix = SecurityConstants.DEFAULT_PROJECT_PREFIX +".social.wx",name = "app-id")
public class WXAutoConfig extends SocialAutoConfigurerAdapter {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    protected ConnectionFactory<?> createConnectionFactory() {
        WXProperties wxProperties = securityProperties.getSocial().getWx();
        return new WXConnectionFactory(wxProperties.getProviderId(),wxProperties.getAppId(),wxProperties.getAppSecret());
    }

    @Bean({"connect/weixinConnect", "connect/weixinConnected"})
    @ConditionalOnMissingBean(name = "wXConnectProviderView")
    public View wXConnectProviderView(){
       return new ConnectProviderView();
    }

}
