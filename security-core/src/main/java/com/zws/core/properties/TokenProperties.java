package com.zws.core.properties;

import com.zws.core.token.store.TokenStoreEnum;
import lombok.Data;

/**
 * @Author: Gosin
 * @Date: 2018/10/27 21:12
 * @Email: 2848392861@qq.com
 */
@Data
public class TokenProperties {

    private TokenStoreEnum storeType;

    private int maximumTokens=0;

    private Boolean exceptionIfMaximumExceeded=false;

    private Boolean supportRefreshToken = true;

    private Boolean reuseRefreshToken = true;


}
