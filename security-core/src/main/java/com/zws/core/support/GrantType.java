package com.zws.core.support;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/10/17
 */
public enum GrantType {

    AUTHORIZATION_CODE("authorization_code"),

    PASSWORD("password"),

    CLIENT_CREDENTIALS("client_credentials"),

    IMPLICIT("implicit"),

    REFRESH_TOKEN("refresh_token"),

    SMS("sms"),

    PASSWORD_CODE("password_code"),

    OPENID("openid");


    private String value;

    GrantType(String value){
        this.value = value;
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


}
