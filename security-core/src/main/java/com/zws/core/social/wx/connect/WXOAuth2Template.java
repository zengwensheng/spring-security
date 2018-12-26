package com.zws.core.social.wx.connect;

import com.zws.core.support.JsonUtils;
import com.zws.core.support.SecurityEnum;
import com.zws.core.support.SimpleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.social.security.SocialAuthenticationException;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/10/9
 */
@Slf4j
public class WXOAuth2Template extends OAuth2Template {


    private String clientId;

    private String accessTokenUrl;

    private String clientSecret;

    private static final String REFRESH_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token";


    public WXOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl){
        super(clientId,clientSecret,authorizeUrl,accessTokenUrl);
        this.clientId = clientId;
        this.accessTokenUrl = accessTokenUrl;
        this.clientSecret = clientSecret;
    }

    public AccessGrant refreshAccess(String refreshToken, MultiValueMap<String, String> additionalParameters) {

        StringBuilder refreshTokenUrl = new StringBuilder(REFRESH_TOKEN_URL);

        refreshTokenUrl.append("?appid="+clientId);
        refreshTokenUrl.append("&grant_type=refresh_token");
        refreshTokenUrl.append("&refresh_token="+refreshToken);

        return getAccessToken(refreshTokenUrl.toString());
    }


    public String buildAuthenticateUrl(OAuth2Parameters parameters) {
        String url = super.buildAuthenticateUrl(parameters);
        url = url + "&appid="+clientId+"&scope=snsapi_login#wechat_redirect";
        return url;
    }

    public String buildAuthorizeUrl(OAuth2Parameters parameters) {
        return buildAuthenticateUrl(parameters);
    }




    public AccessGrant exchangeForAccess(String authorizationCode, String redirectUri, MultiValueMap<String, String> additionalParameters) {
        StringBuilder accessTokenRequestUrl = new StringBuilder(accessTokenUrl);
        accessTokenRequestUrl.append("?appid="+clientId);
        accessTokenRequestUrl.append("&secret="+clientSecret);
        accessTokenRequestUrl.append("&code="+authorizationCode);
        accessTokenRequestUrl.append("&grant_type=authorization_code");
        return getAccessToken(accessTokenRequestUrl.toString());
    }

    public AccessGrant getAccessToken(String accessTokenUrl){
        String resultStr = getRestTemplate().getForObject(accessTokenUrl, String.class);
        Map<String, Object> result =  JsonUtils.readValue(resultStr,Map.class);
        if(result==null||result.containsKey("errcode")){
            log.error("##############获取微信token错误："+resultStr+"###############");
            throw new SocialAuthenticationException(new SimpleResponse(SecurityEnum.SOCIAL_WX_ACCESS_TOKEN_ERROR).toString());
        }
        WXAccessGrant wxAccessGrant = new WXAccessGrant((String) result.get("access_token"),(String) result.get("scope"),(String) result.get("refresh_token"), getIntegerValue(result, "expires_in"), (String) result.get("openid"));
        return wxAccessGrant;
    }

    private Long getIntegerValue(Map<String, Object> map, String key) {
        try {
            return Long.valueOf(String.valueOf(map.get(key))); // normalize to String before creating integer value;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    protected RestTemplate createRestTemplate() {
        RestTemplate restTemplate  =   super.createRestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return restTemplate;
    }
}
