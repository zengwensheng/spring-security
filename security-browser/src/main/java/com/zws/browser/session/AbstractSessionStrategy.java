/**
 * 
 */
package com.zws.browser.session;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zws.core.properties.SecurityProperties;
import com.zws.core.support.JsonUtils;
import com.zws.core.support.SecurityConstants;
import com.zws.core.support.SecurityEnum;
import com.zws.core.support.SimpleResponse;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/9/29
 */
@Data
public abstract class AbstractSessionStrategy {

	private final Logger logger = LoggerFactory.getLogger(getClass());


	private String destinationUrl;

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	private boolean createNewSession = true;


	private String sessionKey;


	public AbstractSessionStrategy(String invalidSessionUrl,String sessionKey) {
		Assert.isTrue(UrlUtils.isValidRedirectUrl(invalidSessionUrl), "url must start with '/' or with 'http(s)'");
		this.destinationUrl = invalidSessionUrl;
		this.sessionKey= sessionKey;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.web.session.InvalidSessionStrategy#
	 * onInvalidSessionDetected(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	protected void onSessionInvalid(HttpServletRequest request, HttpServletResponse response) throws IOException {

		if (createNewSession) {
			request.getSession();
		}

		String sourceUrl = request.getRequestURI();
		String targetUrl;
		Cookie[] cookies =  request.getCookies();
		for(Cookie cookie:cookies){
			if(sessionKey.equals(cookie.getName())){
				cookie.clone();
			}
		}

		if (StringUtils.endsWithIgnoreCase(sourceUrl, SecurityConstants.URL_SUFFIX)) {
			targetUrl = destinationUrl;
			redirectStrategy.sendRedirect(request, response, targetUrl);
		}else{
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.setContentType(SecurityConstants.DEFAULT_CONTENT_TYPE);
			response.getWriter().write(JsonUtils.writeValueAsString(new SimpleResponse(getErrorEnum())));
		}
		
	}


    abstract SecurityEnum getErrorEnum();

	public void setCreateNewSession(boolean createNewSession) {
		this.createNewSession = createNewSession;
	}

}
