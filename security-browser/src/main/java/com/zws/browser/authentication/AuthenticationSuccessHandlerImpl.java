package com.zws.browser.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zws.core.properties.LoginResponseType;
import com.zws.core.support.JsonUtils;
import com.zws.core.support.SecurityConstants;
import com.zws.core.properties.SecurityProperties;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/9/28
 */
@Data
public class AuthenticationSuccessHandlerImpl extends SavedRequestAwareAuthenticationSuccessHandler {

    private SecurityProperties  securityProperties;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if(securityProperties.getBrowser().getLoginType()==LoginResponseType.JSON) {
            response.setStatus(HttpStatus.OK.value());
            response.setContentType(SecurityConstants.DEFAULT_CONTENT_TYPE);
            response.getWriter().write(JsonUtils.writeValueAsString(authentication));
        }else{
            super.onAuthenticationSuccess(request,response,authentication);
        }
    }
}
