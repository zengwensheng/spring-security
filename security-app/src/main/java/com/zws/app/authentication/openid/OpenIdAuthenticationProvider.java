package com.zws.app.authentication.openid;

import com.zws.core.authentication.sms.SmsCodeAuthenticationToken;
import com.zws.core.authentication.sms.SmsCodeUserDetailsService;
import lombok.Data;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;

import javax.annotation.security.DenyAll;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/10/17
 */
@Data
public class OpenIdAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    private UserDetailsChecker userDetailsChecker;

    private UsersConnectionRepository usersConnectionRepository;

    private ConnectionSignUp connectionSignUp;



    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        OpenIdAuthenticationToken openIdAuthenticationToken= (OpenIdAuthenticationToken) authentication;


        Set<String> providerUserIds =  new HashSet<>();
        providerUserIds.add((String) openIdAuthenticationToken.getPrincipal());

        Set<String> userIds =   usersConnectionRepository.findUserIdsConnectedTo(openIdAuthenticationToken.getProviderId(),providerUserIds);


        if(userIds==null||userIds.size()!=1){
            throw  new InternalAuthenticationServiceException("无法获取用户信息");
        }


        UserDetails user = userDetailsService.loadUserByUsername(userIds.iterator().next());

        if(user==null){
            throw new InternalAuthenticationServiceException("无法获取用户信息");
        }

        userDetailsChecker.check(user);

        OpenIdAuthenticationToken authenticationTokenResult = new OpenIdAuthenticationToken(user,openIdAuthenticationToken.getProviderId(),user.getAuthorities());
        authenticationTokenResult.setDetails(authentication.getDetails());

        return authenticationTokenResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OpenIdAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
