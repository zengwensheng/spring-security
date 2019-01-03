package com.zws.client.authentication.passwordcoce;

import com.zws.client.authentication.sms.SmsResourceDetails;
import com.zws.core.support.GrantType;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.resource.UserApprovalRequiredException;
import org.springframework.security.oauth2.client.resource.UserRedirectRequiredException;
import org.springframework.security.oauth2.client.token.AccessTokenProvider;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.OAuth2AccessTokenSupport;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Iterator;
import java.util.List;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/11/7
 */
public class PasswordCodeAccessTokenProvider extends OAuth2AccessTokenSupport implements AccessTokenProvider {



    @Override
    public boolean supportsResource(OAuth2ProtectedResourceDetails resource) {
        return resource instanceof PasswordCodeResourceDetails && GrantType.PASSWORD_CODE.getValue().equals(resource.getGrantType());
    }

    @Override
    public boolean supportsRefresh(OAuth2ProtectedResourceDetails resource) {
        return supportsResource(resource);
    }

    @Override
    public OAuth2AccessToken obtainAccessToken(OAuth2ProtectedResourceDetails details, AccessTokenRequest request) throws UserRedirectRequiredException, UserApprovalRequiredException, AccessDeniedException {
        PasswordCodeResourceDetails resource = (PasswordCodeResourceDetails) details;
        return retrieveToken(request, resource, getParametersForTokenRequest(resource, request), new HttpHeaders());
    }

    private MultiValueMap<String, String> getParametersForTokenRequest(PasswordCodeResourceDetails resource, AccessTokenRequest request) {

        MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
        form.putAll(request);

        if (resource.isScoped()) {

            StringBuilder builder = new StringBuilder();
            List<String> scope = resource.getScope();

            if (scope != null) {
                Iterator<String> scopeIt = scope.iterator();
                while (scopeIt.hasNext()) {
                    builder.append(scopeIt.next());
                    if (scopeIt.hasNext()) {
                        builder.append(' ');
                    }
                }
            }

            form.set("scope", builder.toString());
        }

        return form;

    }


    @Override
    public OAuth2AccessToken refreshAccessToken(OAuth2ProtectedResourceDetails resource, OAuth2RefreshToken refreshToken, AccessTokenRequest request) throws UserRedirectRequiredException {
        return null;
    }
}
