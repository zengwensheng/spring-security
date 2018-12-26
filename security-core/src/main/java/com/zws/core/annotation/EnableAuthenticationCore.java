package com.zws.core.annotation;

import com.zws.core.authentication.sms.SmsCodeAuthenticationSecurityConfig;
import com.zws.core.config.SecurityCoreConfig;
import com.zws.core.properties.SecurityProperties;
import com.zws.core.social.SocialConfig;
import com.zws.core.social.qq.config.QQAutoConfig;
import com.zws.core.social.wx.config.WXAutoConfig;
import com.zws.core.token.TokenStoreConfig;
import com.zws.core.validate.ValidateCodeBeanConfig;
import com.zws.core.validate.ValidateSecurityConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/11/2
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableConfigurationProperties(SecurityProperties.class)
@Import({SecurityCoreConfig.class
        ,ValidateSecurityConfig.class
        ,ValidateCodeBeanConfig.class
        ,SmsCodeAuthenticationSecurityConfig.class
        ,SocialConfig.class
        ,QQAutoConfig.class
        ,WXAutoConfig.class
        ,TokenStoreConfig.class
})
public @interface EnableAuthenticationCore {
}
