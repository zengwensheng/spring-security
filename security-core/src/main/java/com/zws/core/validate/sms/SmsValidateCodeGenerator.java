package com.zws.core.validate.sms;

import com.zws.core.properties.SecurityProperties;
import com.zws.core.validate.ValidateCode;
import com.zws.core.validate.ValidateCodeGenerator;
import lombok.Data;
import org.apache.commons.lang.RandomStringUtils;


/**
 * @Author: Gosin
 * @Date: 2018/10/4 14:43
 * @Email: 2848392861@qq.com
 */
@Data
public class SmsValidateCodeGenerator implements ValidateCodeGenerator {

    private SecurityProperties securityProperties;

    @Override
    public ValidateCode generator() {
        String code = RandomStringUtils.randomNumeric(securityProperties.getCode().getSms().getLength());
        return new ValidateCode(code,securityProperties.getCode().getSms().getExpireIn());
    }
}
