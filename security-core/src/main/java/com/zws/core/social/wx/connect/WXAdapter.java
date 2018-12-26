package com.zws.core.social.wx.connect;

import com.zws.core.social.wx.api.WX;
import com.zws.core.social.wx.api.WXUserInfo;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/10/9
 */
public class WXAdapter implements ApiAdapter<WX> {

    private String openId;

    public WXAdapter(){}

    public WXAdapter(String openId){
        this.openId = openId;
    }

    @Override
    public boolean test(WX WX) {
        return true;
    }

    @Override
    public void setConnectionValues(WX WX, ConnectionValues connectionValues) {
        WXUserInfo wxUserInfo = WX.getUserInfo(openId);
        connectionValues.setProviderUserId(wxUserInfo.getOpenid());
        connectionValues.setDisplayName(wxUserInfo.getNickname());
        connectionValues.setImageUrl(wxUserInfo.getHeadimgurl());
    }

    @Override
    public UserProfile fetchUserProfile(WX WX) {
        return null;
    }

    @Override
    public void updateStatus(WX WX, String s) {

    }
}
