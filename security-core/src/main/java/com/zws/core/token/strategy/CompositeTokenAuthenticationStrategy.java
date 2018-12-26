package com.zws.core.token.strategy;

import com.zws.core.token.exception.TokenAuthenticationException;
import lombok.Data;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/10/26
 */
@Data
public class CompositeTokenAuthenticationStrategy implements TokenAuthenticationStrategy {


    private List<TokenAuthenticationStrategy> tokenAuthenticationStrategyList = new ArrayList<>();

    @Override
    public void onAuthentication(OAuth2Authentication authentication) throws TokenAuthenticationException {
          tokenAuthenticationStrategyList.forEach(tokenAuthenticationStrategy -> {
              tokenAuthenticationStrategy.onAuthentication(authentication);
          });
    }

    public void addTokenAuthenticationStrategy(TokenAuthenticationStrategy tokenAuthenticationStrategy){
        tokenAuthenticationStrategyList.add(tokenAuthenticationStrategy);
    }
}
