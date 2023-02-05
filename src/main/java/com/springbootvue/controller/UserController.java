package com.springbootvue.controller;

import com.springbootvue.entity.User;
import com.springbootvue.mapper.UserMapper;
import com.springbootvue.service.UserService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController
{

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @PostMapping("")
    public Integer save(@RequestBody User user){
        return userService.save(user);
    }

    @GetMapping("")
    public List<User> index(){
        List<User> userAll = userMapper.findAll();
        return userAll;
    }

    @DeleteMapping("/{id}")
    public Integer delete(@PathVariable Integer id){
        return userMapper.deleteById(id);
    }
}
