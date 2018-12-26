package com.zws.core.properties;

import com.zws.core.support.SecurityConstants;
import lombok.Data;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/10/8
 */
@Data
public class SocialProperties {

    private String filterProcessesUrl = "/auth";



    private QQProperties qq = new QQProperties();

    private WXProperties wx = new WXProperties();
}
