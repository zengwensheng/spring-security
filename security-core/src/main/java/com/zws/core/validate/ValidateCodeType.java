package com.zws.core.validate;

import com.zws.core.support.SecurityConstants;

/**
 * @Author: Gosin
 * @Date: 2018/10/2 9:36
 * @Email: 2848392861@qq.com
 */
public enum  ValidateCodeType {
    SMS {
        @Override
        public String getParamNameOnValidate() {
            return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_SMS;
        }
    },

    IMG {
        @Override
        public String getParamNameOnValidate() {
            return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_IMAGE;
        }
    };

    public abstract String getParamNameOnValidate();
}
