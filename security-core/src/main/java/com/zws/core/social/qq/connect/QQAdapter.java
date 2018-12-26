package com.zws.core.social.qq.connect;

import com.zws.core.social.qq.api.QQ;
import com.zws.core.social.qq.api.QQUserInfo;
import com.zws.core.support.SecurityEnum;
import com.zws.core.support.SimpleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.security.SocialAuthenticationException;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/10/8
 */
@Slf4j
public class QQAdapter implements ApiAdapter<QQ> {

    @Override
    public boolean test(QQ qq) {
        return true;
    }

    @Override
    public void setConnectionValues(QQ qq, ConnectionValues connectionValues) {
        QQUserInfo qqUserInfo = qq.getQQUserInfo();
        connectionValues.setDisplayName(qqUserInfo.getNickname());
        connectionValues.setImageUrl(qqUserInfo.getFigureurl_qq_1());
        connectionValues.setProfileUrl(null);
        connectionValues.setProviderUserId(qqUserInfo.getOpenId());
    }

    @Override
    public UserProfile fetchUserProfile(QQ qq) {
        return null;
    }

    @Override
    public void updateStatus(QQ qq, String s) {

    }
}
