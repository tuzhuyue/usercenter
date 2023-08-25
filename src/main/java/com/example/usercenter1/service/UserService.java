package com.example.usercenter1.service;

import com.example.usercenter1.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* @author 土竹月
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2023-08-23 16:29:30
*/

public interface UserService extends IService<User> {


    /**
     * 用户注册
     * @param userAccount   账户
     * @param userPassword  密码
     * @param checkPassword 确认密码
     * @return  新用户id
     */
    long userRegist(String userAccount, String userPassword, String checkPassword);

    /**
     *
     * @param userAccount   账户
     * @param userPassword  密码
     * @param request       返回
     * @return 用户脱敏后的信息
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户脱敏
     * @param originUser 原始数据
     * @return 脱敏后数据
     */
    User getSafetyUser(User originUser);

    /**
     * 用户注销
     * @param request
     * @return
     */
    int userLogout(HttpServletRequest request);
}
