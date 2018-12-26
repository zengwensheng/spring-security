package com.zws.demo.entity.vo;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/9/28
 */
@Data
public class UserVo {

    public interface SimpleUserView{};
    public interface PasswordUserView extends  SimpleUserView{}


    @ApiModelProperty(name = "用户id")
    @JsonView(SimpleUserView.class)
    private String id;
    @ApiModelProperty(name = "用户名")
    @JsonView(SimpleUserView.class)
    private String username;
    @ApiModelProperty(name = "用户密码")
    @JsonView(PasswordUserView.class)
    private String password;
    @ApiModelProperty(name = "用户年龄")
    @JsonView(SimpleUserView.class)
    private String age;
    @ApiModelProperty(name = "出生日期")
    @JsonView(SimpleUserView.class)
    private Date birthDay;
    @ApiModelProperty(name = "手机号码")
    @JsonView(SimpleUserView.class)
    private String mobile;


}
