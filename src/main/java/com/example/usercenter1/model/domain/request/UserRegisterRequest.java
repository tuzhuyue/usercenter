package com.example.usercenter1.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 记录注册请求参数
 */
@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = 2684432971375282814L;
    private String userAccount;
    private String userPassword;
    private String checkPassword;
}
