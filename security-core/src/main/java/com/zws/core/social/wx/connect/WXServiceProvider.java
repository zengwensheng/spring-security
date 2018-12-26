package com.zws.core.social.wx.connect;

import com.zws.core.social.wx.api.WX;
import com.zws.core.social.wx.api.WXImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/10/9
 */
public class WXServiceProvider extends AbstractOAuth2ServiceProvider<WX> {


    /**
     * 微信获取授权码的url
     */
    private static final String URL_AUTHORIZE = "https://open.weixin.qq.com/connect/qrconnect";
    /**
     * 微信获取accessToken的url
     */
    private static final String URL_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token";


    public WXServiceProvider(String appId, String appSecret){
        super(new WXOAuth2Template(appId,appSecret,URL_AUTHORIZE,URL_ACCESS_TOKEN));
    }

    @Override
    public WX getApi(String accessToken) {
        return new WXImpl(accessToken);
    }
}
