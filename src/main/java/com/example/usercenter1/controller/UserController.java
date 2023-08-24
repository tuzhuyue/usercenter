package com.example.usercenter1.controller;

import com.example.usercenter1.model.domain.User;
import com.example.usercenter1.model.domain.request.UserLoginRequest;
import com.example.usercenter1.model.domain.request.UserRegisterRequest;
import com.example.usercenter1.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 用户接口
 *
 * @author tuzhuyue
 */
@RestController//返回json
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    /**
     * 注册接口
     * @param userRegisterRequest 注册请求参数
     * @return 注册请求
     */
    @PostMapping("/register")
    public Long userRegist(@RequestBody UserRegisterRequest userRegisterRequest){
        //校验参数
        if (userRegisterRequest == null){
            return null;
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)){
            return null;
        }
        return userService.userRegist(userAccount, userPassword, checkPassword);
    }

    /**
     * 登录接口
     * @param userLoginRequest 登录请求参数
     * @param request
     * @return  登录请求
     */
    @PostMapping("/login")
    public User userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request){
        //校验参数
        if (userLoginRequest == null){
            return null;
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();

        if (StringUtils.isAnyBlank(userAccount, userPassword)){
            return null;
        }
        return userService.userLogin(userAccount, userPassword,request);
    }



}
