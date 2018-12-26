package com.zws.core.validate;

import com.zws.core.properties.SecurityProperties;
import com.zws.core.validate.*;
import com.zws.core.validate.image.ImgValidateCodeGenerator;
import com.zws.core.validate.image.ImgValidateCodeHandler;
import com.zws.core.validate.sms.SmsValidateCodeGenerator;
import com.zws.core.validate.sms.SmsValidateCodeHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.util.Map;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/9/29
 */
@Configuration
@ConditionalOnBean(ValidateSecurityConfig.class)
@Import(ValidateCodeController.class)
public class ValidateCodeBeanConfig {


    @Bean
    public ValidateCodeFilter validateFilter(SecurityProperties securityProperties, AuthenticationFailureHandler authenticationFailureHandlerImpl,ValidateCodeHandlerHolder validateCodeHandlerHolder){
        ValidateCodeFilter validateFilter = new ValidateCodeFilter();
        validateFilter.setSecurityProperties(securityProperties);
        validateFilter.setAuthenticationFailureHandlerImpl(authenticationFailureHandlerImpl);
        validateFilter.setValidateCodeHandlerHolder(validateCodeHandlerHolder);
        return validateFilter;
    }

    @Bean
    @ConditionalOnMissingBean(name = "imgValidateCodeGenerator")
    public ImgValidateCodeGenerator imgValidateCodeGenerator(SecurityProperties securityProperties){
        ImgValidateCodeGenerator imgValidateCodeGenerator  = new ImgValidateCodeGenerator();
        imgValidateCodeGenerator.setSecurityProperties(securityProperties);
        return imgValidateCodeGenerator;
    }


    @Bean
    @ConditionalOnMissingBean(name = "smsValidateCodeGenerator")
    public SmsValidateCodeGenerator smsValidateCodeGenerator(SecurityProperties securityProperties){
         SmsValidateCodeGenerator smsValidateCodeGenerator = new SmsValidateCodeGenerator();
         smsValidateCodeGenerator.setSecurityProperties(securityProperties);
         return smsValidateCodeGenerator;
    }

    @Bean
    @ConditionalOnMissingBean(name = "imgValidateCodeHandler")
    public ImgValidateCodeHandler  imgValidateCodeHandler(Map<String,ValidateCodeGenerator> validateGeneratorMap,ValidateCodeRepository validateCodeRepository){
         ImgValidateCodeHandler imgValidateCodeHandler = new ImgValidateCodeHandler();
         imgValidateCodeHandler.setValidateGeneratorMap(validateGeneratorMap);
         imgValidateCodeHandler.setValidateCodeRepository(validateCodeRepository);
         return imgValidateCodeHandler;
    }

    @Bean
    @ConditionalOnMissingBean(name = "smsValidateCodeHandler")
    public SmsValidateCodeHandler smsValidateCodeHandler(Map<String,ValidateCodeGenerator> validateGeneratorMap,ValidateCodeRepository validateCodeRepository){
        SmsValidateCodeHandler smsValidateCodeHandler = new SmsValidateCodeHandler();
        smsValidateCodeHandler.setValidateGeneratorMap(validateGeneratorMap);
        smsValidateCodeHandler.setValidateCodeRepository(validateCodeRepository);
        return smsValidateCodeHandler;
    }


    @Bean
    public ValidateCodeHandlerHolder validateCodeHandlerHolder(Map<String,ValidateCodeHandler> validateCodeHandlerMap){
        ValidateCodeHandlerHolder validateCodeHandlerHolder = new ValidateCodeHandlerHolder();
        validateCodeHandlerHolder.setValidateCodeHandlerMap(validateCodeHandlerMap);
        return validateCodeHandlerHolder;
    }







}
