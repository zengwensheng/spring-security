package com.zws.core.social;

import com.zws.core.social.support.SocialUserInfo;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/10/17
 */
public abstract class SocialController {

    protected SocialUserInfo buildSocialUserInfo(Connection<?> connection) {
        SocialUserInfo userInfo = new SocialUserInfo();
        userInfo.setProviderId(connection.getKey().getProviderId());
        userInfo.setProviderUserId(connection.getKey().getProviderUserId());
        userInfo.setNickname(connection.getDisplayName());
        userInfo.setHeadimg(connection.getImageUrl());
        return userInfo;
    }

    protected SocialUserInfo buildSocialUserInfo(ConnectionData connection) {
        SocialUserInfo userInfo = new SocialUserInfo();
        userInfo.setProviderId(connection.getProviderId());
        userInfo.setProviderUserId(connection.getProviderUserId());
        userInfo.setNickname(connection.getDisplayName());
        userInfo.setHeadimg(connection.getImageUrl());
        return userInfo;
    }
}
