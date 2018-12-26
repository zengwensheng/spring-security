package com.zws.app.authentication;

import com.zws.core.support.SecurityConstants;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class AuthenticationFailureHandlerImpl extends SimpleUrlAuthenticationFailureHandler {



    public AuthenticationFailureHandlerImpl(){
    }


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
            log.error("###########登录失败############",exception);
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContentType(SecurityConstants.DEFAULT_CONTENT_TYPE);
            response.getWriter().write(exception.getMessage());
    }
}
