package com.springbootvue.controller;

import com.springbootvue.entity.User;
import com.springbootvue.mapper.UserMapper;
import com.springbootvue.service.UserService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/page")
    public Map<String, Object> findPage(@RequestParam Integer pageNum,
                                        @RequestParam Integer pageSize,
                                        @RequestParam String username){
        pageNum = (pageNum - 1) * pageSize;

        username = "%" + username + "%";

        List<User> data = userMapper.selectPage(pageNum, pageSize, username);
        Integer total = userMapper.selectTotal(username);

        Map<String, Object> res = new HashMap<>();
        res.put("data", data);
        res.put("total", total);

        System.out.println("res = " + res);
        return res;
    }
}
