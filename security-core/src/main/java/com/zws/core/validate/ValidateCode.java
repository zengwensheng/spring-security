package com.zws.core.validate;

import lombok.Data;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/9/29
 */
@Data
public class ValidateCode implements Serializable {


    private static final long serialVersionUID = 4235997212223595910L;
    private String key;

    private String code;

    private LocalDateTime expireTime;


    public ValidateCode(){}

    public ValidateCode(String code, LocalDateTime expireTime) {
        this.code = code;
        this.expireTime = expireTime;
    }

    public ValidateCode(String code, Long expireIn) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }

    public boolean isExpire(){
         return LocalDateTime.now().isAfter(expireTime);
    }

    public Long getExpireIn(){
        Duration duration = Duration.between(LocalDateTime.now(),expireTime);
        return duration.toMillis()/1000;
    }
}
