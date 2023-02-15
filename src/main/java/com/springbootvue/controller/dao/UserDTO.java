package com.springbootvue.controller.dao;

import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String password;
    private String nickname;
    private String avatarUrl;

    private String token;

}
