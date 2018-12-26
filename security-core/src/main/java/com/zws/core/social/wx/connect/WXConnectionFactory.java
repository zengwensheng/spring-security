package com.zws.core.social.wx.connect;

import com.zws.core.social.wx.api.WX;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.support.OAuth2Connection;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2ServiceProvider;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/10/9
 */
public class WXConnectionFactory extends OAuth2ConnectionFactory<WX> {

    public WXConnectionFactory(String providerId, String appId, String appSecret){
        super(providerId,new WXServiceProvider(appId,appSecret),new WXAdapter());
    }

    protected String extractProviderUserId(AccessGrant accessGrant) {
        if(accessGrant instanceof  WXAccessGrant){
            return ((WXAccessGrant)accessGrant).getOpenId();
        }
        return null;
    }

    public Connection<WX> createConnection(AccessGrant accessGrant) {
        return new OAuth2Connection<WX>(getProviderId(), extractProviderUserId(accessGrant), accessGrant.getAccessToken(),
                accessGrant.getRefreshToken(), accessGrant.getExpireTime(), getOAuth2ServiceProvider(), getApiAdapter(accessGrant));
    }

    private OAuth2ServiceProvider<WX> getOAuth2ServiceProvider() {
        return (OAuth2ServiceProvider<WX>) getServiceProvider();
    }

    protected ApiAdapter<WX> getApiAdapter(AccessGrant accessGrant) {
        return new WXAdapter(extractProviderUserId(accessGrant)) ;
    }
}
