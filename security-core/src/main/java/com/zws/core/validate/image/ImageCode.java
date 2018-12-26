package com.zws.core.validate.image;


import com.zws.core.validate.ValidateCode;
import lombok.Data;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/9/29
 */
@Data
public class ImageCode extends ValidateCode {

   private BufferedImage image;

    public ImageCode() { }

    public ImageCode(String code, LocalDateTime expireTime, BufferedImage image) {
        super(code, expireTime);
        this.image = image;
    }

    public ImageCode(String code, Long expireIn, BufferedImage image) {
        super(code, expireIn);
        this.image = image;
    }
}
