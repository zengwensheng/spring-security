package com.zws.demo.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.zws.core.properties.SecurityProperties;
import com.zws.demo.entity.dto.UserDto;
import com.zws.demo.entity.vo.UserVo;
import com.zws.demo.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/9/28
 */
@RestController
@RequestMapping("/user")
@Api(value="/user", tags="用户模块")
public class UserController {

    @Autowired
    private UserService userDetailServiceImpl;
    @Autowired
    private ProviderSignInUtils providerSignInUtils;

    @Autowired
    private SecurityProperties securityProperties;

    @GetMapping("/{id:\\d+}")
    @ApiOperation(value = "查询用户详情")
    @JsonView(UserVo.PasswordUserView.class)
    public UserVo getUserInfo(@PathVariable String id){
       UserVo userVo =new UserVo();
       userVo.setId("1");
       userVo.setUsername("zws");
       userVo.setAge("20");
       userVo.setPassword("123456");
       userVo.setBirthDay(new Date());
       return userVo;
    }

    @GetMapping("/jwt/me")
    public Object getCurrentUser(HttpServletRequest request) throws Exception {
        String token = StringUtils.substringAfter(request.getHeader("Authorization"), "bearer ");
        Claims claims = Jwts.parser().setSigningKey(securityProperties.getApp().getJwtSigningKey().getBytes("UTF-8"))
					.parseClaimsJws(token).getBody();
        return claims;
    }

    @GetMapping("/me")
    public Object getCurrentUser(@AuthenticationPrincipal UserDetails user) {
        return user;
    }

    @GetMapping
    @ApiOperation(value = "用户列表查询")
    @JsonView(UserVo.SimpleUserView.class)
    public List<UserVo> getUserList(){
        List<UserVo> userVoList = new ArrayList<>();
        UserVo userVo =new UserVo();
        userVo.setId("1");
        userVo.setUsername("zws");
        userVo.setAge("20");
        userVo.setPassword("123456");
        userVo.setBirthDay(new Date());
        userVoList.add(userVo);
        return userVoList;
    }

    @PostMapping
    @ApiOperation(value = "用户添加")
    public UserVo createUser(@RequestBody @Valid UserDto userDto, BindingResult bindingResult){
         return userDetailServiceImpl.insert(userDto);
    }


    @PostMapping("/binding")
    @ApiOperation(value = "绑定")
    public  void binding(UserDto userDto, HttpServletRequest request){
        UserVo userVo = userDetailServiceImpl.insert(userDto);
        providerSignInUtils.doPostSignUp(userVo.getUsername(),new ServletWebRequest(request));
    }

/*
    @Autowired(required = false)
    private AppProviderSignInUtils appProviderSignInUtils;

    @PostMapping("/appBinding")
    @ApiOperation(value = "绑定")
    public  void appBinding(UserDto userDto, HttpServletRequest request){
        UserVo userVo = userDetailServiceImpl.insert(userDto);
        appProviderSignInUtils.doPostSignUp(userVo.getUsername(),new ServletWebRequest(request));
    }*/


}
