package com.zws.core.properties;

import lombok.Data;
import org.springframework.boot.autoconfigure.social.SocialProperties;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/10/8
 */
@Data
public class QQProperties extends SocialProperties {

    public String providerId = "qq";
}
