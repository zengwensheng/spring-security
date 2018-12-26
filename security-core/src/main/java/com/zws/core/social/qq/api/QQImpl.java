package com.zws.core.social.qq.api;

import com.zws.core.support.SecurityEnum;
import com.zws.core.support.JsonUtils;
import com.zws.core.support.SimpleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;
import org.springframework.social.security.SocialAuthenticationException;

import java.util.Map;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/10/8
 */
@Slf4j
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {


    private static final String URL_GET_OPENID = "https://graph.qq.com/oauth2.0/me?access_token=%s";

    private static final String URL_GET_USER_INFO = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";


    private String openId;

    private String appId;


    public QQImpl(String accessToken, String appId) {
        super(accessToken, TokenStrategy.ACCESS_TOKEN_PARAMETER);
        this.appId = appId;
        String url = String.format(URL_GET_OPENID, accessToken);
        String result = getRestTemplate().getForObject(url, String.class);
        result= result.replace("callback( ","").replace(" )","");
        Map<String,String> resultMap = JsonUtils.readValue(result,Map.class);
        if(resultMap==null||resultMap.size()==0||resultMap.containsKey("code")){
            log.error("############获取open_id错误:"+result+"############");
            throw new SocialAuthenticationException(new SimpleResponse(SecurityEnum.SOCIAL_QQ_OPEN_ID_ERROR).toString());
        }
        this.openId = resultMap.get("openid");

    }


    @Override
    public QQUserInfo getQQUserInfo() {
        String url = String.format(URL_GET_USER_INFO, appId, openId);
        String result = getRestTemplate().getForObject(url, String.class);
        QQUserInfo qqUserInfo;
        try {
            qqUserInfo = JsonUtils.readValue(result.replace("\n",""), QQUserInfo.class);
            qqUserInfo.setOpenId(openId);
            return qqUserInfo;
        } catch (Exception e) {
            log.error("############获取用户信息错误:"+result+"############");
            throw new SocialAuthenticationException(new SimpleResponse(SecurityEnum.SOCIAL_QQ_USER_INFO_ERROR).toString());
        }
    }
}
