package com.zws.core.authentication.sms;

import com.zws.core.support.GrantType;
import com.zws.core.support.SecurityConstants;
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
 * date 2018/10/8
 */

public class SmsCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    // ~ Static fields/initializers
    // =====================================================================================



    private String smsParameter = SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_SMS;

    private boolean postOnly = true;


    // ~ Constructors
    // ===================================================================================================

    public SmsCodeAuthenticationFilter() {
        super(new AntPathRequestMatcher(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_SMS, "POST"));
    }


    // ~ Methods
    // ========================================================================================================

    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }

        String sms = obtainSmS(request);


        if (sms == null) {
            sms = "";
        }


        sms = sms.trim();

        SmsCodeAuthenticationToken authRequest =new SmsCodeAuthenticationToken(sms);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);
        request.setAttribute(SecurityConstants.DEFAULT_GRANT_TYPE_PARAMETER, GrantType.SMS.getValue());
        return this.getAuthenticationManager().authenticate(authRequest);
}

    /**
     * Enables subclasses to override the composition of the username, such as by
     * including additional values and a separator.
     *
     * @param request so that request attributes can be retrieved
     *
     * @return the username that will be presented in the <code>Authentication</code>
     * request token to the <code>AuthenticationManager</code>
     */
    protected  String obtainSmS(HttpServletRequest request){
        return request.getParameter(smsParameter);
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
                              SmsCodeAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    /**
     * Sets the parameter name which will be used to obtain the username from the login
     * request.
     *
     * @param smsParameter the parameter name. Defaults to "username".
     */
    public void setSmsParameter(String smsParameter) {
        Assert.hasText(smsParameter, "Username parameter must not be empty or null");
        this.smsParameter = smsParameter;
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

    public final String getSmsParameter() {
        return smsParameter;
    }



}
