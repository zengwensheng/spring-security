package com.zws.core.social.wx.connect;

import lombok.Data;
import org.springframework.social.oauth2.AccessGrant;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/10/9
 */
@Data
public class WXAccessGrant extends AccessGrant {

    private static final long serialVersionUID = -7243374526633186782L;
    private String openId;

    public WXAccessGrant(String accessToken, String scope, String refreshToken, Long expiresIn,String openId){
        super(accessToken,scope,refreshToken,expiresIn);
        this.openId = openId;
    }
}
