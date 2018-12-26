package com.zws.core.properties;

import com.zws.core.support.SecurityConstants;
import lombok.Data;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/10/18
 */
@Data
public class AppProperties {

    private String signUpUrl = SecurityConstants.DEFAULT_APP_SING_UP_URL;

    private String signInUrl = SecurityConstants.DEFAULT_APP_SING_IN_URL;

    private String jwtSigningKey = "12345678";

    private TokenProperties token = new TokenProperties();
}
