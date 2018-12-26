package com.zws.core.token;

import com.zws.core.properties.SecurityProperties;
import com.zws.core.support.SecurityConstants;
import com.zws.core.token.store.CustomRedisTokenStore;
import com.zws.core.token.store.IndexNameOauth2Store;
import com.zws.core.token.strategy.CompositeTokenAuthenticationStrategy;
import com.zws.core.token.strategy.ConcurrentTokenControlAuthenticationStrategy;
import com.zws.core.token.strategy.TokenAuthenticationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.Arrays;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/10/25
 */
@Configuration
public class TokenStoreConfig {

    @Autowired
    private IndexNameOauth2Store tokenStore;
    @Autowired
    private SecurityProperties securityProperties;
    @Autowired(required = false)
    private  ClientDetailsService clientDetailsService;
    @Autowired(required = false)
    private TokenAuthenticationStrategy tokenAuthenticationStrategy;
    @Autowired(required = false)
    private UserDetailsService userDetailsService;
    @Autowired(required = false)
    private TokenEnhancer tokenEnhancer;


    @Bean
    @Primary
    public CustomTokenService tokenService() {
        CustomTokenService customTokenService = new CustomTokenService(!isConcurrentTokenControlEnabled());
        customTokenService.setTokenStore(tokenStore);
        customTokenService.setSupportRefreshToken(securityProperties.getApp().getToken().getSupportRefreshToken());
        customTokenService.setReuseRefreshToken(securityProperties.getApp().getToken().getReuseRefreshToken());
        customTokenService.setClientDetailsService(clientDetailsService);
        customTokenService.setTokenEnhancer(tokenEnhancer);
        if(tokenAuthenticationStrategy==null){
            tokenAuthenticationStrategy =getDefaultTokenAuthenticationStrategy();
        }
        customTokenService.setTokenAuthenticationStrategy(tokenAuthenticationStrategy);
        addUserDetailsService(customTokenService, userDetailsService);
        return customTokenService;
    }

    private boolean isConcurrentTokenControlEnabled() {
        return  securityProperties.getApp().getToken().getMaximumTokens()>0;
    }

    private TokenAuthenticationStrategy getDefaultTokenAuthenticationStrategy(){
        CompositeTokenAuthenticationStrategy compositeTokenAuthenticationStrategy = new CompositeTokenAuthenticationStrategy();
        if(isConcurrentTokenControlEnabled()){
            ConcurrentTokenControlAuthenticationStrategy concurrentTokenControlAuthenticationStrategy = new ConcurrentTokenControlAuthenticationStrategy(
                    tokenStore,
                    securityProperties.getApp().getToken().getMaximumTokens(),
                    securityProperties.getApp().getToken().getExceptionIfMaximumExceeded());
            compositeTokenAuthenticationStrategy.addTokenAuthenticationStrategy(concurrentTokenControlAuthenticationStrategy);
        }
        return compositeTokenAuthenticationStrategy;
    }


    public void addUserDetailsService(CustomTokenService tokenServices, UserDetailsService userDetailsService) {
        if (userDetailsService != null) {
            PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
            provider.setPreAuthenticatedUserDetailsService(new UserDetailsByNameServiceWrapper<PreAuthenticatedAuthenticationToken>(
                    userDetailsService));
            tokenServices
                    .setAuthenticationManager(new ProviderManager(Arrays.<AuthenticationProvider>asList(provider)));
        }
    }



    @Configuration
    @ConditionalOnProperty(prefix = SecurityConstants.DEFAULT_PROJECT_PREFIX + ".app.", name = "token.store-type", havingValue = "none", matchIfMissing = true)
    public static class InMemoryStoreConfig {



    }



    @Configuration
    @ConditionalOnProperty(prefix = SecurityConstants.DEFAULT_PROJECT_PREFIX + ".app.", name = "token.store-type", havingValue = "redis")
    public static class RedisTokenStoreConfig {
        @Autowired
        private RedisConnectionFactory redisConnectionFactory;
        @Autowired
        private RedisOperations<Object, Object> redisTemplate;

        @Bean
        public IndexNameOauth2Store tokenStore() {
            CustomRedisTokenStore redisTokenStore = new CustomRedisTokenStore(redisConnectionFactory,redisTemplate);
            return redisTokenStore;
        }


    }

    @Configuration
    @ConditionalOnProperty(prefix = SecurityConstants.DEFAULT_PROJECT_PREFIX + ".app.", name = "token.store-type", havingValue = "jwt")
    public static class JwtTokenStoreConfig {


        @Bean
        public TokenStore tokenStore(SecurityProperties securityProperties) {
            JwtTokenStore jwtTokenStore = new JwtTokenStore(jwtAccessTokenConverter(securityProperties));
            return jwtTokenStore;
        }


        @Bean
        public JwtAccessTokenConverter jwtAccessTokenConverter(SecurityProperties securityProperties) {
            JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
            jwtAccessTokenConverter.setSigningKey(securityProperties.getApp().getJwtSigningKey());
            return jwtAccessTokenConverter;
        }

        @Bean
        @ConditionalOnBean(TokenEnhancer.class)
        public TokenEnhancer jwtTokenEnhancer() {
            return new TokenJwtEnhancer();
        }


    }




}
