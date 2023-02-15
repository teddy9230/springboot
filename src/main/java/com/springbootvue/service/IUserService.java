package com.springbootvue.service;

import com.springbootvue.controller.dao.UserDTO;
import com.springbootvue.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 林于哲
 * @since 2023-02-12
 */
public interface IUserService extends IService<User> {

    UserDTO  login(UserDTO userDTO);

    User  register(UserDTO userDTO);
}
