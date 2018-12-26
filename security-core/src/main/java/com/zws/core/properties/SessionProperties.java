package com.zws.core.properties;

import com.zws.core.support.SecurityConstants;
import lombok.Data;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/10/12
 */
@Data
public class SessionProperties {

    private String sessionKey=SecurityConstants.DEFAULT_SESSION_KEY;

    private int maximumSessions=1;

    private Boolean maxSessionsPreventsLogin=false;

    private String sessionInvalidUrl = SecurityConstants.DEFAULT_SESSION_INVALID_URL;
}
