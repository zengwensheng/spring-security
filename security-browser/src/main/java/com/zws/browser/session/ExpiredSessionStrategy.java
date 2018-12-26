/**
 * 
 */
package com.zws.browser.session;

import java.io.IOException;

import javax.servlet.ServletException;

import com.zws.core.support.SecurityEnum;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/9/29
 */
public class ExpiredSessionStrategy extends AbstractSessionStrategy implements SessionInformationExpiredStrategy {

	public ExpiredSessionStrategy(String invalidSessionUrl,String sessionKey) {
		super(invalidSessionUrl,sessionKey);
	}


	@Override
	public void  onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
		onSessionInvalid(event.getRequest(), event.getResponse());
	}

	@Override
    SecurityEnum getErrorEnum() {
		return SecurityEnum.SESSION_CONCURRENT;
	}
}
