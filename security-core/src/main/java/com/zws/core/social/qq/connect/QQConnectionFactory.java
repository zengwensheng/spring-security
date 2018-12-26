package com.zws.core.social.qq.connect;

import com.zws.core.social.qq.api.QQ;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/10/8
 */
public class QQConnectionFactory extends OAuth2ConnectionFactory<QQ> {

    public  QQConnectionFactory(String providerId,String appId,String appSecret){
        super(providerId,new QQServiceProvider(appId,appSecret),new QQAdapter());
    }
}
