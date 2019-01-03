package com.zws.client.authentication;

import com.zws.client.authentication.passwordcoce.PasswordCodeResourceDetails;
import com.zws.client.authentication.sms.SmsResourceDetails;
import com.zws.core.properties.SecurityProperties;
import com.zws.core.support.SecurityConstants;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.*;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.util.List;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/11/5
 */
@Configuration
@EnableOAuth2Client
public class OAuth2ClientConfig {


    @Bean
    @ConditionalOnProperty(prefix = SecurityConstants.DEFAULT_PROJECT_PREFIX+".client",name = "password-token-url")
    public OAuth2ProtectedResourceDetails resourceOwnerPasswordResourceDetails(SecurityProperties securityProperties){
        ResourceOwnerPasswordResourceDetails resourceDetails = new ResourceOwnerPasswordResourceDetails();
        resourceDetails.setClientId(securityProperties.getClient().getClientId());
        resourceDetails.setClientSecret(securityProperties.getClient().getClientSecret());
        resourceDetails.setScope(securityProperties.getClient().getScope());
        resourceDetails.setAccessTokenUri(securityProperties.getClient().getPasswordTokenUrl());
        return resourceDetails;
    }

    @Bean
    @ConditionalOnProperty(prefix = SecurityConstants.DEFAULT_PROJECT_PREFIX+".client",name = "password-code-token-url")
    public OAuth2ProtectedResourceDetails passwordCodeResourceDetails(SecurityProperties securityProperties){
        PasswordCodeResourceDetails passwordCodeResourceDetails = new PasswordCodeResourceDetails();
        passwordCodeResourceDetails.setClientId(securityProperties.getClient().getClientId());
        passwordCodeResourceDetails.setClientSecret(securityProperties.getClient().getClientSecret());
        passwordCodeResourceDetails.setScope(securityProperties.getClient().getScope());
        passwordCodeResourceDetails.setAccessTokenUri(securityProperties.getClient().getPasswordCodeTokenUrl());
        return passwordCodeResourceDetails;
    }


    @Bean
    @ConditionalOnProperty(prefix = SecurityConstants.DEFAULT_PROJECT_PREFIX+".client",name = "sms-token-url")
    public OAuth2ProtectedResourceDetails smsResourceDetails(SecurityProperties securityProperties){
        SmsResourceDetails smsResourceDetails = new SmsResourceDetails();
        smsResourceDetails.setClientId(securityProperties.getClient().getClientId());
        smsResourceDetails.setClientSecret(securityProperties.getClient().getClientSecret());
        smsResourceDetails.setScope(securityProperties.getClient().getScope());
        smsResourceDetails.setAccessTokenUri(securityProperties.getClient().getSmsTokenUrl());
        return smsResourceDetails;
    }


    @Bean
    @ConditionalOnMissingBean
    public OAuth2ClientAuthenticationProcessingFilter oAuth2ClientAuthenticationProcessingFilter(SecurityProperties securityProperties, ResourceServerTokenServices resourceServerTokenServices,OAuth2RestOperations oAuth2RestOperations){
         OAuth2ClientAuthenticationProcessingFilter oAuth2ClientAuthenticationProcessingFilter  = new OAuth2ClientAuthenticationProcessingFilter(securityProperties.getClient().getLoginProcessingUrl());
         oAuth2ClientAuthenticationProcessingFilter.setTokenServices(resourceServerTokenServices);
         oAuth2ClientAuthenticationProcessingFilter.setRestTemplate(oAuth2RestOperations);
         oAuth2ClientAuthenticationProcessingFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
         oAuth2ClientAuthenticationProcessingFilter.setAuthenticationFailureHandler(authenticationFailureHandler());
         return oAuth2ClientAuthenticationProcessingFilter;
    }

    @Bean
    @Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
    @Primary
    public OAuth2ClientContext oauth2ClientContext(AccessTokenRequest accessTokenRequest) {
        return new DefaultOAuth2ClientContext(accessTokenRequest);
    }

    @Bean
    @ConditionalOnMissingBean
    public OAuth2RestOperations oAuth2RestOperations(List<OAuth2ProtectedResourceDetails> resourceDetailsList, AccessTokenRequest accessTokenRequest){
        OAuth2GrantTypeRestTemplate oauth2GrantTypeRestTemplate = new OAuth2GrantTypeRestTemplate(resourceDetailsList,oauth2ClientContext(accessTokenRequest));
        return oauth2GrantTypeRestTemplate;
    }

    @Bean
    @ConditionalOnMissingBean
    public AuthenticationSuccessHandler authenticationSuccessHandler(){
        AuthenticationSuccessHandlerImpl authenticationSuccessHandler = new AuthenticationSuccessHandlerImpl();
        return authenticationSuccessHandler;
    }

    @Bean
    @ConditionalOnMissingBean
    public AuthenticationFailureHandler authenticationFailureHandler(){
        AuthenticationFailureHandlerImpl authenticationFailureHandler = new AuthenticationFailureHandlerImpl();
        return authenticationFailureHandler;
    }









}
