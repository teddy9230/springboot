package com.springbootvue.config.interceptor;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.springbootvue.common.Constants;
import com.springbootvue.entity.User;
import com.springbootvue.exception.ServiceException;
import com.springbootvue.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private IUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        String token = request.getHeader("token");

        if(!(handler instanceof HandlerMethod)){
            return true;
        }

        if(StrUtil.isBlank(token)){
            throw new ServiceException(Constants.CODE_401, "沒有 token ，請重新登入!!");
        }

        String userId;
        try{
            userId = JWT.decode(token).getAudience().get(0);
        }catch (JWTDecodeException jx){
            throw new ServiceException(Constants.CODE_401, "token 驗證失敗!!");
        }

        User user = userService.getById(userId);
        if( user == null){
            throw new ServiceException(Constants.CODE_401, "使用者不存在，請重新登入!!");
        }

        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();

        try {
            jwtVerifier.verify(token);
        } catch(JWTVerificationException jx){
            throw new ServiceException(Constants.CODE_401, "token 驗證失敗!!，請重新登入!!");
        }

        return true;
    }
}
