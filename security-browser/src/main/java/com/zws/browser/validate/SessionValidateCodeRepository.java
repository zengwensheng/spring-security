package com.zws.browser.validate;

import com.zws.core.validate.ValidateCode;
import com.zws.core.validate.ValidateCodeRepository;
import lombok.Data;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/10/12
 */
@Data
public class SessionValidateCodeRepository implements ValidateCodeRepository {

    private SessionStrategy sessionStrategy =new HttpSessionSessionStrategy();

    private HttpServletRequest request;

    @Override
    public void save(ValidateCode validateCode) {
        sessionStrategy.setAttribute(new ServletWebRequest(request),validateCode.getKey(),validateCode);
    }

    @Override
    public ValidateCode get(String key) {
        Object validateCode = sessionStrategy.getAttribute(new ServletWebRequest(request),key);
        if(validateCode==null){
            return null;
        }
        return (ValidateCode) validateCode;
    }

    @Override
    public void remove(String key) {
         sessionStrategy.removeAttribute(new ServletWebRequest(request),key);
    }
}
