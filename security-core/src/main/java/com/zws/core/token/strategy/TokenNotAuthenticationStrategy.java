package com.zws.core.token.strategy;

import com.zws.core.token.exception.TokenAuthenticationException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

/**
 * @Author: Gosin
 * @Date: 2018/10/27 21:30
 * @Email: 2848392861@qq.com
 */
public class TokenNotAuthenticationStrategy implements TokenAuthenticationStrategy {

    @Override
    public void onAuthentication(OAuth2Authentication authentication) throws TokenAuthenticationException {

    }
}
