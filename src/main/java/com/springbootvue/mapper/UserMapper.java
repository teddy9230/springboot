package com.springbootvue.mapper;

import com.springbootvue.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM sys_user")
    List<User> findAll();

    @Insert("INSERT INTO sys_user(username, password, nickname, email, phone, address) VALUE (#{username}, #{password}, #{nickname}, #{email}, #{phone}, #{address})")
    int insert(User user);

//    @Update("UPDATE sys_user set username = #{username}, password = #{password}, nickname = #{nickname}, email = #{email}, phone = #{phone}, address = #{address} WHERE id = #{id}")
    int update(User user);

    @Delete("DELETE FROM sys_user WHERE id = #{id}")
    Integer deleteById(@Param("id") Integer id);

//    @Select("SELECT * FROM sys_user WHERE username like concat('%', #{username}, '%') limit #{pageNum}, #{pageSize}")
    @Select("SELECT * FROM sys_user WHERE username like #{username} limit #{pageNum}, #{pageSize}")
    List<User> selectPage(Integer pageNum, Integer pageSize, String username);

    @Select("SELECT COUNT(*) FROM sys_user WHERE username like concat('%', #{username}, '%')")
    Integer selectTotal(String username);
}
