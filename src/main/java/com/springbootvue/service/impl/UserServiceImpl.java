package com.springbootvue.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.log.Log;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.springbootvue.common.Constants;
import com.springbootvue.controller.dao.UserDTO;
import com.springbootvue.entity.User;
import com.springbootvue.exception.ServiceException;
import com.springbootvue.mapper.UserMapper;
import com.springbootvue.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.springbootvue.utils.TokenUtils;
import org.springframework.stereotype.Service;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 林于哲
 * @since 2023-02-12
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private static final Log LOG = Log.get();

    private User getUserInfo(UserDTO userDTO){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userDTO.getUsername());
        queryWrapper.eq("password", userDTO.getPassword());

        User one;

        try {
            one = getOne(queryWrapper);
        } catch (Exception ex) {
            LOG.error(ex);
            throw new ServiceException(Constants.CODE_500, "系統錯誤!!");
        }

        return one;
    }

    @Override
    public UserDTO login(UserDTO userDTO) {
//        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("username", userDTO.getUsername());
//        queryWrapper.eq("password", userDTO.getPassword());

//        方法一
//        List<User> list = list(queryWrapper);
//        return list.size() != 0;

//        方法二
//        如果只有一個正常 有多個會報錯

//        User one;
//
//        try {
//            one = getOne(queryWrapper);
//        } catch (Exception ex) {
//            LOG.error(ex);
//            throw new ServiceException(Constants.CODE_500, "系統錯誤!!");
//        }

        User one = getUserInfo(userDTO);

        if (one != null){
            BeanUtil.copyProperties(one, userDTO, true);

            String token = TokenUtils.genToken(one.getId().toString(), one.getPassword());
            userDTO.setToken(token);

            return userDTO;
        }else{
            throw new ServiceException(Constants.CODE_600, "使用者或密碼錯誤!!");
            
        }


    }

    @Override
    public User register(UserDTO userDTO) {
        User one = getUserInfo(userDTO);

        if (one == null) {
            one = new User();
            BeanUtil.copyProperties(userDTO, one, true);
            save(one);
        }else{
            throw new ServiceException(Constants.CODE_600, "使用者已存在!!");
        }

        return one;
    }


}
