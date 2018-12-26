package com.zws.app.social;

import com.zws.app.support.AppSecretException;
import com.zws.core.support.SecurityConstants;
import com.zws.core.support.SecurityEnum;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.web.context.request.WebRequest;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/10/18
 */
@Data
public class AppProviderSignInUtils {


    private static final String KEY_PREFIX= SecurityConstants.DEFAULT_PROJECT_PREFIX+":security:social.connect. ";

    private static final String KEY_PARAMETER="device-id";

    private static final Long REDIS_EXPIRED = 10L;

    private RedisTemplate<Object,Object> redisTemplate;

    private UsersConnectionRepository usersConnectionRepository;


    private ConnectionFactoryLocator connectionFactoryLocator;

    public void saveConnectionData(WebRequest webRequest, ConnectionData connectionData){
        redisTemplate.opsForValue().set(getKey(webRequest),connectionData,REDIS_EXPIRED,TimeUnit.MINUTES);
    }


    public ConnectionData getConnectionData(WebRequest webRequest){
        String key = getKey(webRequest);
        if (!redisTemplate.hasKey(key)) {
            throw new AppSecretException(SecurityEnum.SOCIAL_APP_USER_IS_NULL);
        }
        return (ConnectionData)redisTemplate.opsForValue().get(key);
    }

    public void doPostSignUp(String userId,WebRequest webRequest){
        String key = getKey(webRequest);
        if (!redisTemplate.hasKey(key)) {
            throw new AppSecretException(SecurityEnum.SOCIAL_APP_USER_IS_NULL);
        }
        ConnectionData connectionData = (ConnectionData)redisTemplate.opsForValue().get(key);
        Connection<?> connection = connectionFactoryLocator.getConnectionFactory(connectionData.getProviderId())
                .createConnection(connectionData);
        usersConnectionRepository.createConnectionRepository(userId).addConnection(connection);

        redisTemplate.delete(key);
    }

    protected  String getKey(WebRequest webRequest){
        String deviceId = webRequest.getHeader(KEY_PARAMETER);
        if (StringUtils.isBlank(deviceId)) {
            throw new AppSecretException(SecurityEnum.SOCIAL_APP_PARAMETER_IS_NULL);
        }
        return KEY_PARAMETER + deviceId;
    }
}
