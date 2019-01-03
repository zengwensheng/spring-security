package com.zws.client.authentication.sms;

import com.zws.core.support.GrantType;
import org.springframework.security.oauth2.client.resource.BaseOAuth2ProtectedResourceDetails;

public class SmsResourceDetails extends BaseOAuth2ProtectedResourceDetails {

    public SmsResourceDetails(){
        setGrantType(GrantType.SMS.getValue());
    }

}
