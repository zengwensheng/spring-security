package com.zws.core.authentication.sms;

import lombok.Data;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/10/8
 */
@Data
public class SmsCodeAuthenticationProvider implements AuthenticationProvider {

    private SmsCodeUserDetailsService smsCodeUserDetailsService;

    private UserDetailsChecker userDetailsChecker;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsCodeAuthenticationToken smsCodeAuthenticationToken =(SmsCodeAuthenticationToken) authentication;

        UserDetails user = smsCodeUserDetailsService.loadUserBySms((String) smsCodeAuthenticationToken.getPrincipal());

        if(user==null){
            throw new InternalAuthenticationServiceException(
                    "smsCodeUserDetailsService returned null, which is an interface contract violation");
        }

        userDetailsChecker.check(user);

        SmsCodeAuthenticationToken authenticationTokenResult = new SmsCodeAuthenticationToken(user,user.getAuthorities());
        authenticationTokenResult.setDetails(authentication.getDetails());

        return authenticationTokenResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
