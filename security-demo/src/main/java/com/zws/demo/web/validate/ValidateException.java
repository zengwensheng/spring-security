package com.zws.demo.web.validate;

import lombok.Data;
import org.springframework.validation.ObjectError;

import java.util.List;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/9/28
 */
@Data
public class ValidateException extends RuntimeException{

    private static final long serialVersionUID = -4033877326715070699L;

    private List<ObjectError> errors;

    public ValidateException() {
    }

    public ValidateException( List<ObjectError> errors) {
        this.errors = errors;
    }
}
