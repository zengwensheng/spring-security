package com.zws.core.validate.image;

import com.zws.core.support.SecurityConstants;
import com.zws.core.support.SecurityEnum;
import com.zws.core.validate.AbstractValidateCodeHandler;
import com.zws.core.validate.ValidateCodeException;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;

/**
 * @Author: Gosin
 * @Date: 2018/10/2 11:51
 * @Email: 2848392861@qq.com
 */
public class ImgValidateCodeHandler extends AbstractValidateCodeHandler<ImageCode> {

    private final static String DEVICE_ID_PARAMETER = "device-id";
    private final static String KEY_PREFIX= SecurityConstants.DEFAULT_PROJECT_PREFIX+":validate:img:";

    @Override
    protected void send(ImageCode imageCode, ServletWebRequest servletWebRequest)  {
        try {
            ImageIO.write(imageCode.getImage(), "JPEG", servletWebRequest.getResponse().getOutputStream());
        }catch (Exception e){
            throw  new ValidateCodeException(SecurityEnum.VALIDATE_IMAGE_SEND_ERROR);
        }
    }

    @Override
    protected String getKey(ServletWebRequest servletWebRequest){
        String key = servletWebRequest.getParameter(DEVICE_ID_PARAMETER);
        if(StringUtils.isEmpty(key)){
            throw new ValidateCodeException(SecurityEnum.VALIDATE_IMG_PARAM_EMPTY);
        }
        return KEY_PREFIX+key;
    }

}
