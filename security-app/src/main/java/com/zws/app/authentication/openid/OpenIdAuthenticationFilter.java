package com.zws.app.authentication.openid;

import com.zws.core.support.GrantType;
import com.zws.core.support.SecurityConstants;
import lombok.Data;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/10/17
 */
@Data
public class OpenIdAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    // ~ Static fields/initializers
    // =====================================================================================

    private final static String DEFAULT_PARAMETER_NAME_OPEN_ID="open-id";

    private final static String DEFAULT_PARAMETER_NAME_PROVIDER_ID="provider-id";

    private String  openIdParameter = DEFAULT_PARAMETER_NAME_OPEN_ID;

    private String  providerIdParameter = DEFAULT_PARAMETER_NAME_PROVIDER_ID;

    private boolean postOnly = true;


    // ~ Constructors
    // ===================================================================================================

    public OpenIdAuthenticationFilter() {
        super(new AntPathRequestMatcher(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_OPEN_ID, "POST"));
    }

    // ~ Methods
    // ========================================================================================================

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }

        String openId = obtainOpenId(request);

        String providerId = obtainProviderId(request);

        if (openId == null) {
            openId = "";
        }

        if(providerId == null){
            providerId = "";
        }

        openId = openId.trim();

        providerId = providerId.trim();


        OpenIdAuthenticationToken authRequest =new OpenIdAuthenticationToken(openId,providerId);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);
        request.setAttribute(SecurityConstants.DEFAULT_GRANT_TYPE_PARAMETER, GrantType.OPENID.getValue());
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    /**
     * Enables subclasses to override the composition of the openId, such as by
     * including additional values and a separator.
     *
     * @param request so that request attributes can be retrieved
     *
     * @return the openId that will be presented in the <code>Authentication</code>
     * request token to the <code>AuthenticationManager</code>
     */
    protected String obtainOpenId(HttpServletRequest request){
        return request.getParameter(openIdParameter);
    }


    /**
     * Enables subclasses to override the composition of the providerId, such as by
     * including additional values and a separator.
     *
     * @param request so that request attributes can be retrieved
     *
     * @return the providerId that will be presented in the <code>Authentication</code>
     * request token to the <code>AuthenticationManager</code>
     */

    protected String obtainProviderId(HttpServletRequest request){
        return request.getParameter(providerIdParameter);
    }

    /**
     * Provided so that subclasses may configure what is put into the authentication
     * request's details property.
     *
     * @param request that an authentication request is being created for
     * @param authRequest the authentication request object that should have its details
     * set
     */
    protected void setDetails(HttpServletRequest request,
                              OpenIdAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    /**
     * Sets the parameter name which will be used to obtain the openId from the login
     * request.
     *
     * @param openIdParameter the parameter name. Defaults to "openId".
     */
    public void setOpenIdParameter(String openIdParameter) {
        Assert.hasText(openIdParameter, "OpenId parameter must not be empty or null");
        this.openIdParameter = openIdParameter;
    }


    /**
     * Defines whether only HTTP POST requests will be allowed by this filter. If set to
     * true, and an authentication request is received which is not a POST request, an
     * exception will be raised immediately and authentication will not be attempted. The
     * <tt>unsuccessfulAuthentication()</tt> method will be called as if handling a failed
     * authentication.
     * <p>
     * Defaults to <tt>true</tt> but may be overridden by subclasses.
     */
    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getOpenIdParameter() {
        return openIdParameter;
    }

    public String getProviderIdParameter() {
        return providerIdParameter;
    }

    public void setProviderIdParameter(String providerIdParameter) {
        this.providerIdParameter = providerIdParameter;
    }
}
