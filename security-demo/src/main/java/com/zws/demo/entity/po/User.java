package com.zws.demo.entity.po;

import io.swagger.models.auth.In;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/9/28
 */
@Data
public class User {

    private String id;
    private String username;
    private String password;
    private String age;
    private Date birthDay;
    private String mobile;
}
