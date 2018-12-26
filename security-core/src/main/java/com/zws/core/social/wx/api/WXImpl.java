package com.zws.core.social.wx.api;

import com.zws.core.support.JsonUtils;
import com.zws.core.support.SecurityEnum;
import com.zws.core.support.SimpleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;
import org.springframework.social.security.SocialAuthenticationException;
import java.util.Map;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/10/9
 */
@Slf4j
public class WXImpl extends AbstractOAuth2ApiBinding implements WX {



    private static final String URL_GET_USER_INFO = "https://api.weixin.qq.com/sns/userinfo?openid=";


    public WXImpl(String accessToken){
        super(accessToken,TokenStrategy.ACCESS_TOKEN_PARAMETER);
    }

    @Override
    public WXUserInfo getUserInfo(String openId) {
        String url = URL_GET_USER_INFO+openId;
        String result = getRestTemplate().getForObject(url,String.class);

        Map<String,String> resultMap = JsonUtils.readValue(result,Map.class);
        if(resultMap==null||resultMap.containsKey("errorcode")){
            log.error("########## 获取用户信息错误："+result +"##########");
            throw new SocialAuthenticationException(new SimpleResponse(SecurityEnum.SOCIAL_WX_USER_INFO_ERROR).toString());
        }
        WXUserInfo wXUserInfo = JsonUtils.readValue(result,WXUserInfo.class);
        if(wXUserInfo==null){
            log.error("########## 获取json解析异常："+result +"##########");
            throw new SocialAuthenticationException(new SimpleResponse(SecurityEnum.SOCIAL_WX_USER_INFO_ERROR).toString());
        }
        return wXUserInfo;
    }
}
