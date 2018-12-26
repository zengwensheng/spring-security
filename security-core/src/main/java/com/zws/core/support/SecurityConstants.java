package com.zws.core.support;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/9/29
 */
public interface SecurityConstants {




    String DEFAULT_LOGIN_PROCESSING_URL = "/login";

    String DEFAULT_LOGIN_PROCESSING_URL_SMS = "/login/sms";

    String DEFAULT_LOGIN_PROCESSING_URL_OPEN_ID = "/login/openId";

    String DEFAULT_VALIDATE_CODE_URL_PREFIX = "/code";

    String DEFAULT_UN_AUTHENTICATION_URL = "/authentication";


    String DEFAULT_LOGIN_PAGE_URL ="/login.html";

    String DEFAULT_FAILURE_URL = "/login_error.html";

    String DEFAULT_CONTENT_TYPE = "application/json;charset=UTF-8";

    String DEFAULT_PARAMETER_NAME_CODE_SMS="sms";

    String DEFAULT_PARAMETER_NAME_CODE_IMAGE ="img";


    String DEFAULT_SESSION_INVALID_URL = "/session/invalid.html";

    String DEFAULT_SOCIAL_USER_INFO_URL = "/social/user";

    String DEFAULT_SESSION_KEY = "JSESSIONID";

    String DEFAULT_PROJECT_PREFIX="authentication.core";

    String DEFAULT_APP_SING_IN_URL = "/signIn";

    String DEFAULT_APP_SING_UP_URL = "/signUp";

    String URL_SUFFIX =".html";

    String DEFAULT_GRANT_TYPE_PARAMETER = "grant_type";


}
