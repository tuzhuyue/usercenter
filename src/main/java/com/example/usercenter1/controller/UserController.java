package com.example.usercenter1.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.usercenter1.constant.UserConstant;
import com.example.usercenter1.model.domain.User;
import com.example.usercenter1.model.domain.request.UserLoginRequest;
import com.example.usercenter1.model.domain.request.UserRegisterRequest;
import com.example.usercenter1.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static com.example.usercenter1.constant.UserConstant.ADMIN_ROLE;
import static com.example.usercenter1.constant.UserConstant.USER_LOGIN_STATE;


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
    public Long userRegist(@RequestBody UserRegisterRequest userRegisterRequest) {
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

    /**
     * 用户注销
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public Integer userLogout(HttpServletRequest request){
        //校验参数
        if (request == null){
            return null;
        }
        return userService.userLogout(request);
    }

    /**
     * 获取用户登录态
     * @param request
     * @return
     */
    @GetMapping("/current")
    public  User getCurrentUser(HttpServletRequest request){
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser =(User) userObj;
        if (currentUser == null){
            return null;
        }
        long userId = currentUser.getId();
        //todo 校验用户是否合法
        User user = userService.getById(userId);
        return userService.getSafetyUser(user);
    }
    /**
     * 查询
     * @param username
     * @return
     */
    @GetMapping("/search")
    public List<User> serchUsers(String username,HttpServletRequest request){
        //仅管理员
        if (!isAdmin(request)){
            return new ArrayList<>();
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)){
            queryWrapper.like("username",username);
        }
        List<User> userList = userService.list(queryWrapper);
        return userList.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());
    }

    /**
     * 注销
     * @param id
     * @param request
     * @return
     */
    @PostMapping("delete")
    public boolean deleteUser(@RequestBody long id,HttpServletRequest request){
        //仅管理员
        if (!isAdmin(request)){
            return false;
        }
        if (id <= 0){
            return false;
        }
        return userService.removeById(id);
    }

    /**
     * 判断是否为管理员
     * @param request
     * @return
     */
    private boolean isAdmin(HttpServletRequest request){
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        return user != null && user.getUserRole() == ADMIN_ROLE;
    }


}
