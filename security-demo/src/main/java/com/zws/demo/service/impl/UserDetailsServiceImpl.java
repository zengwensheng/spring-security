package com.zws.demo.service.impl;

import com.zws.core.authentication.sms.SmsCodeUserDetailsService;
import com.zws.core.support.SecurityEnum;
import com.zws.core.support.JsonUtils;
import com.zws.core.support.SimpleResponse;
import com.zws.demo.entity.dto.UserDto;
import com.zws.demo.entity.vo.UserVo;
import com.zws.demo.mapper.UserMapper;
import com.zws.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/9/28
 */

@Component
public class UserDetailsServiceImpl implements UserDetailsService, UserService,SmsCodeUserDetailsService ,SocialUserDetailsService {


    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.zws.demo.entity.po.User user = userMapper.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(JsonUtils.writeValueAsString(new SimpleResponse(SecurityEnum.LOGIN_USERNAME_NOT_EXIST)));
        }
        return builderUser(user);
    }

    @Override
    public UserDetails loadUserBySms(String sms) throws UsernameNotFoundException {
        com.zws.demo.entity.po.User user = userMapper.getUserByMobile(sms);
        if (user == null) {
            throw new UsernameNotFoundException(JsonUtils.writeValueAsString(new SimpleResponse(SecurityEnum.LOGIN_USERNAME_NOT_EXIST)));
        }
        return builderUser(user);
    }

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        com.zws.demo.entity.po.User user = userMapper.getUserByUsername(userId);
        if (user == null) {
            throw new UsernameNotFoundException(JsonUtils.writeValueAsString(new SimpleResponse(SecurityEnum.LOGIN_USERNAME_NOT_EXIST)));
        }
        return builderUser(user);
    }

    private SocialUserDetails builderUser(com.zws.demo.entity.po.User user){
        return new SocialUser(user.getUsername(),user.getPassword(),true, true, true, true, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }

    @Override
    public UserVo insert(UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userMapper.insert(userDto);
        UserVo userVo = new UserVo();
        userVo.setId(userDto.getId());
        userVo.setAge(userDto.getAge());
        userVo.setUsername(userDto.getUsername());
        userVo.setBirthDay(userDto.getBirthDay());
        return userVo;
    }


}
