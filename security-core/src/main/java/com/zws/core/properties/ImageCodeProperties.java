package com.zws.core.properties;

import lombok.Data;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/9/29
 */
@Data
public class ImageCodeProperties extends SmsCodeProperties{

    private Integer width=100;
    private Integer height=20;

    public ImageCodeProperties(){
        super(4);
    }


}
