package com.zws.app.authentication;

import com.zws.app.social.AppProviderSignInUtils;
import com.zws.core.social.SocialController;
import com.zws.core.social.support.SocialUserInfo;
import com.zws.core.support.SecurityConstants;
import com.zws.core.support.SecurityEnum;
import com.zws.core.support.SimpleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/10/18
 */
@RestController
public class AppSecurityController extends SocialController {


    @Autowired
    private ProviderSignInUtils providerSignInUtils;
    @Autowired
    private AppProviderSignInUtils appProviderSignInUtils;


    @RequestMapping(SecurityConstants.DEFAULT_SOCIAL_USER_INFO_URL)
    public SocialUserInfo getSocialUserInfo(HttpServletRequest request){
        ConnectionData connectionData = appProviderSignInUtils.getConnectionData(new ServletWebRequest(request));
        return buildSocialUserInfo(connectionData);
    }

    @RequestMapping(SecurityConstants.DEFAULT_APP_SING_UP_URL)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public SocialUserInfo signUp(HttpServletRequest request){
        Connection<?> connection =  providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
        appProviderSignInUtils.saveConnectionData(new ServletWebRequest(request),connection.createData());
        return buildSocialUserInfo(connection);
    }

    @RequestMapping(SecurityConstants.DEFAULT_APP_SING_IN_URL)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public SimpleResponse signIn(){
        return new SimpleResponse(SecurityEnum.SOCIAL_LOGIN_ERROR);
    }
}
