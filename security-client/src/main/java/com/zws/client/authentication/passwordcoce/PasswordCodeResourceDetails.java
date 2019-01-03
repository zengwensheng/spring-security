package com.zws.client.authentication.passwordcoce;

import com.zws.core.support.GrantType;
import org.springframework.security.oauth2.client.resource.BaseOAuth2ProtectedResourceDetails;

public class PasswordCodeResourceDetails extends BaseOAuth2ProtectedResourceDetails {

    public PasswordCodeResourceDetails(){
        setGrantType(GrantType.PASSWORD_CODE.getValue());

    }

}
