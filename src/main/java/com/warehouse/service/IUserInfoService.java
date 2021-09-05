package com.warehouse.service;


import com.warehouse.model.UserInfo;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface IUserInfoService {

    Integer saveUser(UserInfo userInfo);

    List<UserInfo> getAllUsers();

    UserInfo getOneUserDetailByEmailId(String email);

    void updateUserStatus(Integer id , String status);

    void updateUserPassword(String email , String password) throws UsernameNotFoundException;

    Integer getOtpUsingEmail(String email) throws UsernameNotFoundException;

    void activateUser(String email , Integer otp );
}
