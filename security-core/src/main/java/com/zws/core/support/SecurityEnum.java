package com.zws.core.support;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/9/30
 */
public enum SecurityEnum {
    SYSTEM_ERROR(0001,"系统异常"),
    SYSTEM_REPEAT_CONFIG_ANYREQUEST(0002,"重复配置anyRequest"),


    LOGIN_USERNAME_NOT_EXIST(1001,"用户名不存在"),
    NOT_LOGIN(1002,"请先登录"),
    LOGIN_OUT_SUCCESS(1003,"退出成功"),

    VALIDATE_CODE_EMPTY(2001,"请填写验证码"),
    VALIDATE_CODE_NOT_EXIST(2002,"验证码不存在"),
    VALIDATE_CODE_ERROR(2003,"验证码错误"),
    VALIDATE_CODE_EXPIRE(2004,"验证码已过期"),
    VALIDATE__GENERATOR_NOT_EXIST(2005,"验证码生成器不存在"),
    VALIDATE__HANDLER_NOT_EXIST(2006,"验证码处理器不存在"),
    VALIDATE_SMS_EMPTY(2007,"手机号为空"),
    VALIDATE_IMAGE_SEND_ERROR(2008,"验证码发送失败"),
    VALIDATE_IMG_PARAM_EMPTY(2009,"验证码参数不能为空"),


    SOCIAL_QQ_USER_INFO_ERROR(3001,"获取qq用户信息错误"),
    SOCIAL_QQ_OPEN_ID_ERROR(3002,"获取qq openid错误"),
    SOCIAL_QQ_ACCESS_TOKEN_ERROR(3003,"获取qq token 错误"),

    SOCIAL_WX_USER_INFO_ERROR(3011,"获取微信用户信息错误"),
    SOCIAL_WX_ACCESS_TOKEN_ERROR(3012,"获取微信token错误"),

    SOCIAL_APP_PARAMETER_IS_NULL(3020,"设备id不能为空"),
    SOCIAL_APP_USER_IS_NULL(3021,"无法找到缓存的用户社交账号信息"),

    SOCIAL_LOGIN_ERROR(3030,"第三方登录错误"),

    SESSION_INVALID(4001,"session已过期，请重新登录"),
    SESSION_CONCURRENT(4002,"您的账号已在其他地方登录，如不是本人请修改密码"),

    APP_CLIENT_IS_NULL(5001,"第三方信息不能为空"),
    APP_CLIENT_DECODE_ERROR(5002,"无法解码基本身份验证令牌"),
    APP_CLIENT_NOT_EXIST(5003,"第三方信息不存在"),
    APP_CLIENT_SECRET_ERROR(5004,"第三方secret错误"),

    TOKEN_CURRENT_ERROR(6001,"您的账号已在其他地方登录，如不是本人请修改密码"),

    CLIENT_REQUEST_PARAMETER_GRANT_TYPE_ISNULL(7001,"grant_type参数不能为空"),
    CLIENT_REQUEST_PARAMETER_GRANT_TYPE_ERROR(7001,"grant_type参数错误"),
    ;
    private int code;
    private String msg;


    SecurityEnum(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
