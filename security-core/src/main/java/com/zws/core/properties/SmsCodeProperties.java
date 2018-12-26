package com.zws.core.properties;

import lombok.Data;

/**
 * @Author: Gosin
 * @Date: 2018/10/4 12:53
 * @Email: 2848392861@qq.com
 */
@Data
public class SmsCodeProperties {

    private Integer length=6;
    private Long expireIn=60l;
    private String url;

    public SmsCodeProperties(int length){
        this.length= length;
    }

    public SmsCodeProperties(){}
}
