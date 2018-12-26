package com.zws.core.support;

import lombok.Data;

/**
 * @author zsws
 * @email 2848392861@qq.com
 * date 2018/9/28
 */
@Data
public class SimpleResponse {


    private int code;
    private String msg;

    public SimpleResponse(int code,String msg) {
        this.code = code;
        this.msg = msg;
    }

    public SimpleResponse(SecurityEnum securityEnum){
        this.code = securityEnum.getCode();
        this. msg = securityEnum.getMsg();
    }


    @Override
    public String toString() {
       return JsonUtils.writeValueAsString(this);
    }
}
