package com.zws.demo.entity.dto;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/9/28
 */
@Data
public class UserDto {
    @ApiModelProperty(name = "用户id")
    private String id;
    @ApiModelProperty(name = "用户名")
    @NotEmpty(message = "用户名不能为空")
    private String username;
    @ApiModelProperty(name = "用户密码")
    @NotEmpty(message = "密码不能为空")
    private String password;

    @ApiModelProperty(name = "手机号")
    private String mobile;

    @ApiModelProperty(name = "用户年龄")
    @Size(max = 100,min = 1,message = "用户年龄必须在0到100之间")
    private String age;

    @ApiModelProperty(name = "用户出生日期")
    @Past(message = "用户出生日期必须是过去的日期")
    private Date birthDay;





}
