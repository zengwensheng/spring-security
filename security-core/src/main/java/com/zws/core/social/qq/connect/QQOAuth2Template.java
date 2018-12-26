package com.zws.core.social.qq.connect;

import com.zws.core.support.SecurityEnum;
import com.zws.core.support.SimpleResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.social.security.SocialAuthenticationException;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import java.nio.charset.Charset;


/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/10/11
 */
@Slf4j
public class QQOAuth2Template extends OAuth2Template {

    public QQOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl){
        super(clientId,clientSecret,authorizeUrl,accessTokenUrl);
        setUseParametersForClientAuthentication(true);
    }


    protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
        String result =  getRestTemplate().postForObject(accessTokenUrl,parameters,String.class);

        String[] items = StringUtils.splitByWholeSeparatorPreserveAllTokens(result, "&");
        if(items==null||items.length==0||items.length!=3){
            log.error("############ 获取qq Token错误："+result+"###############");
            throw  new SocialAuthenticationException(new SimpleResponse(SecurityEnum.SOCIAL_QQ_ACCESS_TOKEN_ERROR).toString());
        }
        String accessToken = StringUtils.substringAfterLast(items[0], "=");
        Long expiresIn = new Long(StringUtils.substringAfterLast(items[1], "="));
        String refreshToken = StringUtils.substringAfterLast(items[2], "=");

        return new AccessGrant(accessToken,null,refreshToken,expiresIn);
    }

    protected RestTemplate createRestTemplate() {
        RestTemplate restTemplate = super.createRestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return restTemplate;
    }

}
