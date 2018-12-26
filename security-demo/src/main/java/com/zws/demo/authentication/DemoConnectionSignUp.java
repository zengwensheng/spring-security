package com.zws.demo.authentication;

import com.zws.demo.entity.dto.UserDto;
import com.zws.demo.entity.po.User;
import com.zws.demo.entity.vo.UserVo;
import com.zws.demo.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/10/11
 */
//@Component
public class DemoConnectionSignUp implements ConnectionSignUp {


    @Autowired
    private UserService userService;


    @Override
    public String execute(Connection<?> connection) {
        UserDto userDto = new UserDto();
        userDto.setUsername(RandomStringUtils.randomNumeric(8));
        userDto.setPassword("123456");
        UserVo userVo =  userService.insert(userDto);
        return userVo.getUsername();
    }
}
