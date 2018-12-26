package com.zws.core.validate;

import org.springframework.web.context.request.ServletWebRequest;

public interface ValidateCodeRepository {

    void save(ValidateCode validateCode);

    ValidateCode get(String key);

    void remove(String key);
}
