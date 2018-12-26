package com.zws.demo.mapper;

import com.zws.demo.entity.dto.UserDto;
import com.zws.demo.entity.po.User;
import com.zws.demo.entity.vo.UserVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

/**
 * @author zws
 * @email 2848392861@qq.com
 * date 2018/9/29
 */
public interface UserMapper {

     @Select("select * from user where username =#{username} ")
     User getUserByUsername(@Param("username") String username);

     @Select("select * from user where mobile = #{mobile}")
     User getUserByMobile(@Param("mobile")String mobile);

     @Select("select * from user  where id =#{userId}")
     User getUserId(@Param("userId") String userId);

     @Insert("insert into user (id,username,password,age) values (#{id},#{username},#{password},#{age})")
     @SelectKey(keyProperty = "id", resultType = String.class, before = true,
             statement = "select replace(uuid(), '-', '') as id from dual")
             int insert(UserDto userDto);
}
