package com.zws.core.token.strategy;

import com.zws.core.support.SecurityEnum;
import com.zws.core.support.SimpleResponse;
import com.zws.core.token.store.IndexNameOauth2Store;
import com.zws.core.token.exception.TokenAuthenticationException;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/10/26
 */
@Data
public class ConcurrentTokenControlAuthenticationStrategy implements TokenAuthenticationStrategy{

    private final IndexNameOauth2Store indexNameOauth2Store;
    private final int maximumTokens;
    private final boolean exceptionIfMaximumExceeded;


    public ConcurrentTokenControlAuthenticationStrategy(IndexNameOauth2Store indexNameOauth2Store,int maximumTokens,boolean exceptionIfMaximumExceeded ){
        Assert.notNull(indexNameOauth2Store,IndexNameOauth2Store.class.getSimpleName()+"is null");
        this.indexNameOauth2Store = indexNameOauth2Store;
        this.maximumTokens =maximumTokens;
        this.exceptionIfMaximumExceeded=exceptionIfMaximumExceeded;
    }

    @Override
    public void onAuthentication(OAuth2Authentication authentication) throws TokenAuthenticationException {
        final List<OAuth2AccessToken> oAuth2AccessTokens = indexNameOauth2Store.getAllAccessToken(
                authentication.getName());

        int tokenCount = oAuth2AccessTokens.size();
        int allowedTokens = getMaximumTokens(authentication);

        if (tokenCount < allowedTokens) {
            // They haven't got too many login sessions running at present
            return;
        }

        if (allowedTokens == -1) {
            // We permit unlimited logins
            return;
        }

        allowableTokensExceeded(oAuth2AccessTokens, allowedTokens, indexNameOauth2Store,authentication);
    }

    protected void allowableTokensExceeded(List<OAuth2AccessToken> oAuth2AccessTokens,
                                             int allowableTokens, IndexNameOauth2Store indexNameOauth2Store,OAuth2Authentication authentication)
            throws SessionAuthenticationException {
        if (exceptionIfMaximumExceeded || (oAuth2AccessTokens == null)) {
            throw new TokenAuthenticationException(new SimpleResponse(SecurityEnum.TOKEN_CURRENT_ERROR).toString());
        }

        // Determine least recently used session, and mark it for invalidation
        OAuth2AccessToken leastRecentlyUsed = null;

        for (OAuth2AccessToken token : oAuth2AccessTokens) {
            if ((leastRecentlyUsed == null)
                    || token.getExpiresIn()
                    <leastRecentlyUsed.getExpiresIn()) {
                leastRecentlyUsed = token;
            }
        }

       /* leastRecentlyUsed.expireNow();*/
       if(leastRecentlyUsed instanceof  OAuth2AccessToken){
           ((DefaultOAuth2AccessToken)leastRecentlyUsed).setExpiration(new Date());
       }
       indexNameOauth2Store.storeAccessToken(leastRecentlyUsed,authentication);
    }

    protected  int getMaximumTokens(Authentication authentication){
         return maximumTokens;
    }
}
