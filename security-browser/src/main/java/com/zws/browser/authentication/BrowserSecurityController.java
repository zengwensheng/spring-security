package com.zws.browser.authentication;

import com.zws.core.social.SocialController;
import com.zws.core.social.support.SocialUserInfo;
import com.zws.core.support.SecurityEnum;
import com.zws.core.support.SecurityConstants;
import com.zws.core.properties.SecurityProperties;
import com.zws.core.support.SimpleResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/9/29
*/
@RestController
public class BrowserSecurityController extends SocialController {




    private RequestCache requestCache = new HttpSessionRequestCache();

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();


    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    @RequestMapping(SecurityConstants.DEFAULT_UN_AUTHENTICATION_URL)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public SimpleResponse authentication(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SavedRequest savedRequest =  requestCache.getRequest(request,response);
        if(savedRequest!=null){
            String targetUrl = savedRequest.getRedirectUrl();
            if(StringUtils.endsWithIgnoreCase(targetUrl,SecurityConstants.URL_SUFFIX)){
                redirectStrategy.sendRedirect(request,response,securityProperties.getBrowser().getLoginPage());
            }
        }
        return new SimpleResponse(SecurityEnum.NOT_LOGIN);

    }

    @GetMapping(SecurityConstants.DEFAULT_SOCIAL_USER_INFO_URL)
    public SocialUserInfo getSocialUserInfo(HttpServletRequest request) {
        Connection<?> connection = providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
        return buildSocialUserInfo(connection);
    }



}
