package com.zws.core.validate;

import com.zws.core.properties.SecurityProperties;
import com.zws.core.support.SecurityConstants;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/9/29
 */
@Data
public class ValidateCodeFilter extends OncePerRequestFilter  implements InitializingBean{



    private SecurityProperties securityProperties;

    private AuthenticationFailureHandler authenticationFailureHandlerImpl;

    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    private ValidateCodeHandlerHolder validateCodeHandlerHolder;

    private Map<String, ValidateCodeType> urlMap = new HashMap<>();

    private AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL, ValidateCodeType.IMG);
        addUrlToMap(securityProperties.getCode().getImage().getUrl(), ValidateCodeType.IMG);

        urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_SMS, ValidateCodeType.SMS);
        addUrlToMap(securityProperties.getCode().getSms().getUrl(), ValidateCodeType.SMS);
    }

    protected void addUrlToMap(String urlString, ValidateCodeType type) {
        if (StringUtils.isNotBlank(urlString)) {
            String[] urls = StringUtils.splitByWholeSeparatorPreserveAllTokens(urlString, ",");
            for (String url : urls) {
                urlMap.put(url, type);
            }
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
         ValidateCodeType type = getValidateCodeType(request);
        if(type!=null){
            try {
               validateCodeHandlerHolder.findValidateCodeProcessor(type).validate(new ServletWebRequest(request,response));
            }catch (AuthenticationException authenticationException){
                authenticationFailureHandlerImpl.onAuthenticationFailure(request,response,authenticationException);
                return;
            }

        }
        chain.doFilter(request, response);
    }

    private ValidateCodeType getValidateCodeType(HttpServletRequest request) {
        ValidateCodeType result = null;
        if (!StringUtils.equalsIgnoreCase(request.getMethod(), "get")) {
            Set<String> urls = urlMap.keySet();
            for (String url : urls) {
                if (pathMatcher.match(url, request.getRequestURI())) {
                    result = urlMap.get(url);
                }
            }
        }
        return result;
    }
}
