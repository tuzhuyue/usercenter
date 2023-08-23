package com.example.usercenter1.service;

import com.example.usercenter1.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 土竹月
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2023-08-23 16:29:30
*/
public interface UserService extends IService<User> {

    long userRegist(String userAccount, String userPassword, String checkPassword);
}
