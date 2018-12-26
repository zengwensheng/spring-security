package com.zws.browser.session.concurrent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/10/25
 */
@Configuration
public class ConcurrentSessionAutoConfiguration {


    @Configuration
    @ConditionalOnProperty(prefix = "spring.session",name = "store-type",havingValue = "redis")
    public static  class  ConcurrentRedisSessionConfig {

        @Autowired
        private RedisOperationsSessionRepository redisOperationsSessionRepository;

        @Bean
        public SessionRegistry sessionRegistry() {
            FindByIndexNameSessionRepository findByIndexNameSessionRepository = (FindByIndexNameSessionRepository) redisOperationsSessionRepository;
            SpringSessionBackedSessionRegistry springSessionBackedSessionRegistry = new SpringSessionBackedSessionRegistry(findByIndexNameSessionRepository);
            return springSessionBackedSessionRegistry;
        }
    }
}
