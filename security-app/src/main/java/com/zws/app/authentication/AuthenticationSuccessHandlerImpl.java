package com.zws.app.authentication;

import com.zws.core.support.*;
import lombok.Data;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/9/28
 */
@Data
public class AuthenticationSuccessHandlerImpl extends SavedRequestAwareAuthenticationSuccessHandler {

    private final static String CLIENT_INFO_KEY = "Authorization";
    private final static String CLIENT_INFO_PREFIX = "Basic ";
    private final static String CREDENTIALS_CHARSET="UTF-8";
    private ClientDetailsService clientDetailsService;
    private AuthorizationServerTokenServices authorizationServerTokenServices;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String authorization = request.getHeader(CLIENT_INFO_KEY);
        if (StringUtils.isEmpty(authorization) || !StringUtils.startsWith(authorization, CLIENT_INFO_PREFIX)) {
            throw new UnapprovedClientAuthenticationException(new SimpleResponse(SecurityEnum.APP_CLIENT_IS_NULL).toString());
        }

        String[] tokens = extractAndDecodeHeader(authorization,request);
        assert tokens.length == 2;

        String clientId = tokens[0];

        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);


        if(clientDetails==null){
            throw new NoSuchClientException(new SimpleResponse(SecurityEnum.APP_CLIENT_NOT_EXIST).toString());
        }

        if(!clientDetails.getClientSecret().equals(tokens[1])){
            throw  new BadCredentialsException(new SimpleResponse(SecurityEnum.APP_CLIENT_SECRET_ERROR).toString());
        }
        String grantType = Objects.toString(request.getAttribute(SecurityConstants.DEFAULT_GRANT_TYPE_PARAMETER));
        if(StringUtils.isEmpty(grantType)||"null".equals(grantType)){
            grantType = GrantType.PASSWORD_CODE.getValue();
        }

        TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_MAP,clientId, clientDetails.getScope(), grantType);
        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);;
        OAuth2AccessToken oAuth2AccessToken =  authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
        response.setContentType(SecurityConstants.DEFAULT_CONTENT_TYPE);
        response.getWriter().write(JsonUtils.writeValueAsString(oAuth2AccessToken));

    }

    private String[] extractAndDecodeHeader(String header, HttpServletRequest request)
            throws IOException {

        byte[] base64Token = header.substring(6).getBytes(CREDENTIALS_CHARSET);
        byte[] decoded;
        try {
            decoded = org.springframework.security.crypto.codec.Base64.decode(base64Token);
        }
        catch (IllegalArgumentException e) {
            throw new BadCredentialsException(
                    new SimpleResponse(SecurityEnum.APP_CLIENT_DECODE_ERROR).toString());
        }

        String token = new String(decoded, CREDENTIALS_CHARSET);

        int delim = token.indexOf(StringConstants.COLON);

        if (delim == -1) {
            throw new BadCredentialsException(new SimpleResponse(SecurityEnum.APP_CLIENT_IS_NULL).toString());
        }
        return new String[] { token.substring(0, delim), token.substring(delim + 1) };
    }


}
