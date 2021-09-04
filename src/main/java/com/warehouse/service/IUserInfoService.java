package com.warehouse.service;


import com.warehouse.model.UserInfo;

import java.util.List;

public interface IUserInfoService {

    Integer saveUser(UserInfo userInfo);

    List<UserInfo> getAllUsers();

    UserInfo getOneUserDetailByEmailId(String email);

    void updateUserStatus(Integer id , String status);
}
