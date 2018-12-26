package com.zws.core.social;

import com.zws.core.properties.SecurityProperties;
import com.zws.core.social.support.SocialAuthenticationFilterPostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SpringSocialConfigurer;
import org.springframework.web.servlet.View;

import javax.sql.DataSource;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/10/8
 */
@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {


    @Autowired
    private DataSource dataSource;


    @Autowired(required =  false)
    private ConnectionSignUp connectionSignUp;

    @Autowired(required = false)
    private SocialAuthenticationFilterPostProcessor socialAuthenticationFilterPostProcessor;



    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource,connectionFactoryLocator, Encryptors.noOpText());
        repository.setConnectionSignUp(connectionSignUp);
        return repository;
    }

    @Bean
    public SpringSocialConfigurer customerSocialConfigurer(SecurityProperties securityProperties){
        CustomerSocialConfigurer customerSocialConfigurer = new CustomerSocialConfigurer(securityProperties,socialAuthenticationFilterPostProcessor);
        return customerSocialConfigurer;
    }

    @Bean
    public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator){
        return new ProviderSignInUtils(connectionFactoryLocator,getUsersConnectionRepository(connectionFactoryLocator));
    }

    @Bean("connect/status")
    @ConditionalOnMissingBean(name = "connectStatusView")
    public View connectStatusView(){
        return new ConnectStatusView();
    }




}
