package com.zws.browser.logout;

import com.zws.core.properties.LoginResponseType;
import com.zws.core.properties.SecurityProperties;
import com.zws.core.support.JsonUtils;
import com.zws.core.support.SecurityConstants;
import com.zws.core.support.SecurityEnum;
import com.zws.core.support.SimpleResponse;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/10/15
 */
@Data
public class BrowserLogoutSuccessHandler implements LogoutSuccessHandler {

    private SecurityProperties securityProperties;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (securityProperties.getBrowser().getLoginType()==LoginResponseType.JSON) {
            response.setContentType(SecurityConstants.DEFAULT_CONTENT_TYPE);
            response.getWriter().write(JsonUtils.writeValueAsString(new SimpleResponse(SecurityEnum.LOGIN_OUT_SUCCESS)));
        } else {
            response.sendRedirect(securityProperties.getBrowser().getSignOutUrl());
        }
    }
}
