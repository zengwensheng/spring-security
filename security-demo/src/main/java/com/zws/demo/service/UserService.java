package com.zws.demo.service;

import com.zws.demo.entity.dto.UserDto;
import com.zws.demo.entity.vo.UserVo;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/9/29
 */
public interface UserService {

    UserVo insert(UserDto userDto);
}
