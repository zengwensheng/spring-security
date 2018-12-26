package com.zws.app.validate;

import com.zws.core.validate.ValidateCode;
import com.zws.core.validate.ValidateCodeRepository;
import lombok.Data;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/10/12
 */
@Data
public class RedisValidateCodeRepository implements ValidateCodeRepository {


    private RedisTemplate<Object,Object> redisTemplate;

    @Override
    public void save(ValidateCode validateCode) {
        redisTemplate.opsForValue().set(validateCode.getKey(),validateCode,validateCode.getExpireIn(),TimeUnit.SECONDS);
    }

    @Override
    public ValidateCode get(String key) {
        Object validateCode = redisTemplate.opsForValue().get(key);
        if(validateCode==null){
            return null;
        }
        return (ValidateCode) validateCode;
    }

    @Override
    public void remove(String key) {
        redisTemplate.delete(key);
    }
}
