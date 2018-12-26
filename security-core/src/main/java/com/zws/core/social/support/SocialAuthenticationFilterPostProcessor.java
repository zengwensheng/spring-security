package com.zws.core.social.support;

import org.springframework.social.security.SocialAuthenticationFilter;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/10/17
 */
public interface SocialAuthenticationFilterPostProcessor {
    /**
     * @param socialAuthenticationFilter
     */
    void process(SocialAuthenticationFilter socialAuthenticationFilter);
}
