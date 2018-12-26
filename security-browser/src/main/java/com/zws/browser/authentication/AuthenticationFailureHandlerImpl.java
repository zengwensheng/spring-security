package com.zws.browser.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zws.core.properties.LoginResponseType;
import com.zws.core.support.SecurityConstants;
import com.zws.core.properties.SecurityProperties;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

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
public class AuthenticationFailureHandlerImpl extends SimpleUrlAuthenticationFailureHandler {

    private SecurityProperties securityProperties;


    public AuthenticationFailureHandlerImpl(String defaultFailureUrl){
        super(defaultFailureUrl);
    }


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if(securityProperties.getBrowser().getLoginType() ==LoginResponseType.JSON) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContentType(SecurityConstants.DEFAULT_CONTENT_TYPE);
            response.getWriter().write(exception.getMessage());
        }else{
            super.onAuthenticationFailure(request,response,exception);
        }
    }
}
