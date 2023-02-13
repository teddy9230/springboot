package com.springbootvue.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.springbootvue.service.IUserService;
import com.springbootvue.entity.User;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 林于哲
 * @since 2023-02-12
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    @PostMapping
    public boolean save(@RequestBody User user){
        return userService.saveOrUpdate(user);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id){
        return userService.removeById(id);
    }

    @PostMapping("/del/batch")
    public boolean batchDelete(@RequestBody List<Integer> ids){
        return userService.removeBatchByIds(ids);
    }

    @GetMapping("")
    public List<User> findAll(){
        return userService.list();
    }

    @GetMapping("/{id}")
    public User findOne(@PathVariable Integer id){
        return userService.getById(id);
    }

    @GetMapping("/page")
    public Page<User> findPage(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                               @RequestParam(defaultValue = "") String username,
                               @RequestParam(defaultValue = "") String nickname,
                               @RequestParam(defaultValue = "") String email,
                               @RequestParam(defaultValue = "") String address) {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        if (!"".equals(username)) {
            queryWrapper.like("username", username);
        }

        if (!"".equals(nickname)) {
            queryWrapper.like("nickname", nickname);
//            queryWrapper.and(a -> a.like("nickname", nickname));
        }

        if (!"".equals(email)) {
            queryWrapper.like("email", email);
//            queryWrapper.and(a -> a.like("email", email));
        }

        if (!"".equals(address)) {
            queryWrapper.like("address", address);
//            queryWrapper.and(a -> a.like("address", address));
        }

        queryWrapper.orderByDesc("id");

        return userService.page(new Page<>(pageNum, pageSize), queryWrapper);
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception{
//        第一步
        List<User> list = userService.list();

//        第二步
        ExcelWriter writer = ExcelUtil.getWriter(true);
//        writer.addHeaderAlias("username", "使用者名稱");
//        writer.addHeaderAlias("password", "使用者密碼");
//        writer.addHeaderAlias("nickname", "使用者暱稱");
//        writer.addHeaderAlias("email", "使用者信箱");
//        writer.addHeaderAlias("phone", "使用者電話");
//        writer.addHeaderAlias("address", "使用者地址");
//        writer.addHeaderAlias("createTime", "創立時間");
//        writer.addHeaderAlias("avatarUrl", "使用者頭像");

//        第三步
        writer.write(list, true);

//        第四步
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("會員資料", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();
    }

    @PostMapping("/import")
    public Boolean imp(MultipartFile file ) throws Exception{
        InputStream inputStream =  file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);

//        方法一 表頭要跟 JavaBean 對應起來
//        List<User> list = reader.readAll(User.class);

//        方法二 表頭會忽略 只讀取內容
        List<List<Object>> list = reader.read(1);
        List<User> users = CollUtil.newArrayList();

        for (List<Object> row : list){
            User user = new User();
            user.setUsername(row.get(0).toString());
            user.setPassword(row.get(1).toString());
            user.setNickname(row.get(2).toString());
            user.setEmail(row.get(3).toString());
            user.setPhone(row.get(4).toString());
            user.setAddress(row.get(5).toString());
            user.setAvatarUrl(row.get(6).toString());

            users.add(user);
        }

        System.out.println("List = " + list);
        userService.saveBatch(users);
        return true;
    }



}

