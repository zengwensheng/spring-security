package com.zws.core.validate;

import com.zws.core.support.SecurityEnum;
import lombok.Data;

import java.util.Map;

/**
 * @Author: Gosin
 * @Date: 2018/10/4 11:58
 * @Email: 2848392861@qq.com
 */
@Data
public class ValidateCodeHandlerHolder {


    private Map<String, ValidateCodeHandler> validateCodeHandlerMap;

    public ValidateCodeHandler findValidateCodeProcessor(ValidateCodeType validateCodeType) {
        return findValidateCodeProcessor(validateCodeType.getParamNameOnValidate());
    }

    public ValidateCodeHandler findValidateCodeProcessor(String type) {
        String name = type.toLowerCase() + ValidateCodeHandler.class.getSimpleName();
        ValidateCodeHandler validateCodeHandler = validateCodeHandlerMap.get(name);
        if (validateCodeHandler == null) {
              throw new ValidateCodeException(SecurityEnum.VALIDATE__HANDLER_NOT_EXIST);
        }
        return validateCodeHandler;
    }


}
