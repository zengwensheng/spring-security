package com.zws.core.validate.sms;

import com.zws.core.support.SecurityEnum;
import com.zws.core.support.SecurityConstants;
import com.zws.core.validate.AbstractValidateCodeHandler;
import com.zws.core.validate.ValidateCode;
import com.zws.core.validate.ValidateCodeException;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * @Author: Gosin
 * @Date: 2018/10/4 14:42
 * @Email: 2848392861@qq.com
 */
public class SmsValidateCodeHandler extends AbstractValidateCodeHandler {


    private final static String SMS_PARAMETER = SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_SMS;
    private final static String KEY_PREFIX= SecurityConstants.DEFAULT_PROJECT_PREFIX+":validate:sms:";

    @Override
    protected void send(ValidateCode validateCode, ServletWebRequest servletWebRequest){
        String key = obtainSmS(servletWebRequest);
        if(StringUtils.isEmpty(key)){
            throw new ValidateCodeException(SecurityEnum.VALIDATE_SMS_EMPTY);
        }
        System.out.println("####################验证码："+validateCode.getCode()+"############");
    }


    @Override
    protected String getKey(ServletWebRequest servletWebRequest){
        String key = obtainSmS(servletWebRequest);
        if(StringUtils.isEmpty(key)){
            throw new ValidateCodeException(SecurityEnum.VALIDATE_SMS_EMPTY);
        }
        return KEY_PREFIX+key;
    }

    private String obtainSmS(ServletWebRequest servletWebRequest){
        return servletWebRequest.getParameter(SMS_PARAMETER) ;
    }
}
