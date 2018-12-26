package com.zws.core.token.strategy;

import com.zws.core.token.exception.TokenAuthenticationException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/10/25
 */
public interface TokenAuthenticationStrategy {



    void onAuthentication(OAuth2Authentication authentication) throws TokenAuthenticationException;
}
