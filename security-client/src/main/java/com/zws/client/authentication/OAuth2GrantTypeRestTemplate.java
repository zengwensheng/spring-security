package com.zws.client.authentication;

import com.zws.client.authentication.passwordcoce.PasswordCodeAccessTokenProvider;
import com.zws.client.authentication.sms.SmsAccessTokenProvider;
import com.zws.client.support.ClientSecurityException;
import com.zws.core.support.SecurityEnum;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.http.AccessTokenRequiredException;
import org.springframework.security.oauth2.client.http.OAuth2ErrorHandler;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.resource.UserRedirectRequiredException;
import org.springframework.security.oauth2.client.token.AccessTokenProvider;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.implicit.ImplicitAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordAccessTokenProvider;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.web.client.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/11/5
 */
public class OAuth2GrantTypeRestTemplate extends RestTemplate implements OAuth2RestOperations {

    private static final String REQUEST_PARAMETER="grant_type";

    private final List<OAuth2ProtectedResourceDetails> resourceDetailsList;

    private AccessTokenProvider accessTokenProvider = new AccessTokenProviderChain(Arrays.<AccessTokenProvider> asList(
            new AuthorizationCodeAccessTokenProvider(), new ImplicitAccessTokenProvider(),
            new ResourceOwnerPasswordAccessTokenProvider(), new ClientCredentialsAccessTokenProvider(),
            new PasswordCodeAccessTokenProvider(),new SmsAccessTokenProvider()));

    private OAuth2ClientContext context;

    private boolean retryBadAccessTokens = true;

    private OAuth2RequestAuthenticator authenticator = new DefaultOAuth2RequestAuthenticator();

    public OAuth2GrantTypeRestTemplate(List<OAuth2ProtectedResourceDetails> resourceDetailsList) {
        this(resourceDetailsList, new DefaultOAuth2ClientContext());
    }

    public OAuth2GrantTypeRestTemplate(List<OAuth2ProtectedResourceDetails> resourceDetailsList, OAuth2ClientContext context) {
        super();
        if (resourceDetailsList == null||resourceDetailsList.size()==0) {
            throw new IllegalArgumentException("An OAuth2 resource must be supplied.");
        }

        this.resourceDetailsList = resourceDetailsList;
        this.context = context;
       // setErrorHandler(new OAuth2ErrorHandler(findResourceDetailsByGrantType()));
        setErrorHandler(new DefaultResponseErrorHandler());
    }

    /**
     * Strategy for extracting an Authorization header from an access token and the request details. Defaults to the
     * simple form "TOKEN_TYPE TOKEN_VALUE".
     *
     * @param authenticator the authenticator to use
     */
    public void setAuthenticator(OAuth2RequestAuthenticator authenticator) {
        this.authenticator = authenticator;
    }

    /**
     * Flag to determine whether a request that has an existing access token, and which then leads to an
     * AccessTokenRequiredException should be retried (immediately, once). Useful if the remote server doesn't recognize
     * an old token which is stored in the client, but is happy to re-grant it.
     *
     * @param retryBadAccessTokens the flag to set (default true)
     */
    public void setRetryBadAccessTokens(boolean retryBadAccessTokens) {
        this.retryBadAccessTokens = retryBadAccessTokens;
    }

    @Override
    public void setErrorHandler(ResponseErrorHandler errorHandler) {
        if (!(errorHandler instanceof OAuth2ErrorHandler)) {
            //errorHandler = new OAuth2ErrorHandler(errorHandler, findResourceDetailsByGrantType());
            errorHandler = new DefaultResponseErrorHandler();
        }
        super.setErrorHandler(errorHandler);
    }

    @Override
    public OAuth2ProtectedResourceDetails getResource() {
        return findResourceDetailsByGrantType();
    }

    @Override
    protected ClientHttpRequest createRequest(URI uri, HttpMethod method) throws IOException {

        OAuth2AccessToken accessToken = getAccessToken();

        AuthenticationScheme authenticationScheme = findResourceDetailsByGrantType().getAuthenticationScheme();
        if (AuthenticationScheme.query.equals(authenticationScheme)
                || AuthenticationScheme.form.equals(authenticationScheme)) {
            uri = appendQueryParameter(uri, accessToken);
        }

        ClientHttpRequest req = super.createRequest(uri, method);

        if (AuthenticationScheme.header.equals(authenticationScheme)) {
            authenticator.authenticate(findResourceDetailsByGrantType(), getOAuth2ClientContext(), req);
        }
        return req;

    }

    @Override
    protected <T> T doExecute(URI url, HttpMethod method, RequestCallback requestCallback,
                              ResponseExtractor<T> responseExtractor) throws RestClientException {
        OAuth2AccessToken accessToken = context.getAccessToken();
        RuntimeException rethrow = null;
        try {
            return super.doExecute(url, method, requestCallback, responseExtractor);
        }
        catch (AccessTokenRequiredException e) {
            rethrow = e;
        }
        catch (OAuth2AccessDeniedException e) {
            rethrow = e;
        }
        catch (InvalidTokenException e) {
            // Don't reveal the token value in case it is logged
            rethrow = new OAuth2AccessDeniedException("Invalid token for client=" + getClientId());
        }
        if (accessToken != null && retryBadAccessTokens) {
            context.setAccessToken(null);
            try {
                return super.doExecute(url, method, requestCallback, responseExtractor);
            }
            catch (InvalidTokenException e) {
                // Don't reveal the token value in case it is logged
                rethrow = new OAuth2AccessDeniedException("Invalid token for client=" + getClientId());
            }
        }
        throw rethrow;
    }

    /**
     * @return the client id for this resource.
     */
    private String getClientId() {
        return findResourceDetailsByGrantType().getClientId();
    }

    /**
     * Acquire or renew an access token for the current context if necessary. This method will be called automatically
     * when a request is executed (and the result is cached), but can also be called as a standalone method to
     * pre-populate the token.
     *
     * @return an access token
     */
    public OAuth2AccessToken getAccessToken() throws UserRedirectRequiredException {

        OAuth2AccessToken accessToken = context.getAccessToken();

        if (accessToken == null || accessToken.isExpired()) {
            try {
                accessToken = acquireAccessToken(context);
            }
            catch (UserRedirectRequiredException e) {
                context.setAccessToken(null); // No point hanging onto it now
                accessToken = null;
                String stateKey = e.getStateKey();
                if (stateKey != null) {
                    Object stateToPreserve = e.getStateToPreserve();
                    if (stateToPreserve == null) {
                        stateToPreserve = "NONE";
                    }
                    context.setPreservedState(stateKey, stateToPreserve);
                }
                throw e;
            }
        }
        return accessToken;
    }

    /**
     * @return the context for this template
     */
    public OAuth2ClientContext getOAuth2ClientContext() {
        return context;
    }

    protected OAuth2AccessToken acquireAccessToken(OAuth2ClientContext oauth2Context)
            throws UserRedirectRequiredException {

        AccessTokenRequest accessTokenRequest = oauth2Context.getAccessTokenRequest();
        if (accessTokenRequest == null) {
            throw new AccessTokenRequiredException(
                    "No OAuth 2 security context has been established. Unable to access resource '"
                            + this.findResourceDetailsByGrantType().getId() + "'.", findResourceDetailsByGrantType());
        }

        // Transfer the preserved state from the (longer lived) context to the current request.
        String stateKey = accessTokenRequest.getStateKey();
        if (stateKey != null) {
            accessTokenRequest.setPreservedState(oauth2Context.removePreservedState(stateKey));
        }

        OAuth2AccessToken existingToken = oauth2Context.getAccessToken();
        if (existingToken != null) {
            accessTokenRequest.setExistingToken(existingToken);
        }

        OAuth2AccessToken accessToken = null;
        accessToken = accessTokenProvider.obtainAccessToken(findResourceDetailsByGrantType(), accessTokenRequest);
        if (accessToken == null || accessToken.getValue() == null) {
            throw new IllegalStateException(
                    "Access token provider returned a null access token, which is illegal according to the contract.");
        }
        oauth2Context.setAccessToken(accessToken);
        return accessToken;
    }

    protected URI appendQueryParameter(URI uri, OAuth2AccessToken accessToken) {

        try {

            // method signature uses URI not String.
            String query = uri.getRawQuery(); // Don't decode anything here
            String queryFragment = findResourceDetailsByGrantType().getTokenName() + "=" + URLEncoder.encode(accessToken.getValue(), "UTF-8");
            if (query == null) {
                query = queryFragment;
            }
            else {
                query = query + "&" + queryFragment;
            }

            // first form the URI without query and fragment parts, so that it doesn't re-encode some query string chars
            // (SECOAUTH-90)
            URI update = new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(), uri.getPort(), uri.getPath(), null,
                    null);
            // now add the encoded query string and the then fragment
            StringBuffer sb = new StringBuffer(update.toString());
            sb.append("?");
            sb.append(query);
            if (uri.getFragment() != null) {
                sb.append("#");
                sb.append(uri.getFragment());
            }

            return new URI(sb.toString());

        }
        catch (URISyntaxException e) {
            throw new IllegalArgumentException("Could not parse URI", e);
        }
        catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Could not encode URI", e);
        }

    }


    public void setAccessTokenProvider(AccessTokenProvider accessTokenProvider) {
        this.accessTokenProvider = accessTokenProvider;
    }

    private OAuth2ProtectedResourceDetails findResourceDetailsByGrantType(){

        if(context.getAccessTokenRequest().containsKey(REQUEST_PARAMETER)){
            String grantType = Objects.toString(context.getAccessTokenRequest().getFirst(REQUEST_PARAMETER));

            if(StringUtils.isEmpty(grantType)){
                throw  new ClientSecurityException(SecurityEnum.CLIENT_REQUEST_PARAMETER_GRANT_TYPE_ISNULL);
            }

            for(OAuth2ProtectedResourceDetails details:resourceDetailsList){
                if(details!=null&&details.getGrantType().equals(grantType)){
                    return  details;
                }
            }

        }

        throw  new ClientSecurityException(SecurityEnum.CLIENT_REQUEST_PARAMETER_GRANT_TYPE_ERROR);
    }

}
