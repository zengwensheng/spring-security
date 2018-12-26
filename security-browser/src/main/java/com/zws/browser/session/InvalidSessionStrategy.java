/**
 * 
 */
package com.zws.browser.session;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zws.core.support.SecurityEnum;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/9/29
 */
public class InvalidSessionStrategy extends AbstractSessionStrategy implements org.springframework.security.web.session.InvalidSessionStrategy {

	public InvalidSessionStrategy(String invalidSessionUrl,String sessionKey) {
		super(invalidSessionUrl,sessionKey);
	}

	@Override
	public void  onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		onSessionInvalid(request, response);
	}

	@Override
    SecurityEnum getErrorEnum() {
		return SecurityEnum.SESSION_INVALID;
	}
}
