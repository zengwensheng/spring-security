package com.zws.core.properties;

import com.zws.core.support.SecurityConstants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/9/29
 */
@ConfigurationProperties(SecurityConstants.DEFAULT_PROJECT_PREFIX)
@Data
public class SecurityProperties {

    private BrowserProperties browser = new BrowserProperties();

    private List<String> permitUrl=new ArrayList<>();

    private ValidateCodeProperties code = new ValidateCodeProperties();

    private SocialProperties social = new SocialProperties();

    private AppProperties app = new AppProperties();

    private ClientProperties client = new ClientProperties();

}
