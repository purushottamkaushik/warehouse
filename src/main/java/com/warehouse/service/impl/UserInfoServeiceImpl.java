package com.warehouse.service.impl;

import com.warehouse.consts.UserMode;
import com.warehouse.model.UserInfo;
import com.warehouse.repo.UserInfoRepository;
import com.warehouse.service.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserInfoServeiceImpl implements IUserInfoService, UserDetailsService {


    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Integer saveUser(UserInfo userInfo) {
        String password = userInfo.getPassword();
        String encodedPwd = passwordEncoder.encode(password);
        userInfo.setPassword(encodedPwd);
        return userInfoRepository.save(userInfo).getId();
    }

    @Override
    public List<UserInfo> getAllUsers() {
        return userInfoRepository.findAll();
    }

    @Override
    public UserInfo getOneUserDetailByEmailId(String email) {
        Optional<UserInfo> optional = userInfoRepository.findByEmail(email);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new UsernameNotFoundException("User with email "  + email+ " doesnt exists");
        }
    }

    @Override
    @Transactional
    public void updateUserStatus(Integer id, String status) throws UsernameNotFoundException{

       boolean exists =  userInfoRepository.existsById(id);
       if (exists) {
           userInfoRepository.updateUserStatus(id , UserMode.valueOf(status));
       } else {
           throw  new UsernameNotFoundException("User with id " + id +" not found ");
       }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> opt = userInfoRepository.findByEmail(username);
//
//        if (!opt.isPresent()) {  //  If user not present throw user not found exception
//            throw new UsernameNotFoundException("User Doesnt exists ");
//        } else {
//            // 1 . Get the user
//            UserInfo userInfo = opt.get();
//            // 2. Get User Roles(Authorites in spring )
//            Set<GrantedAuthority> authorites = new HashSet<>();
//
//            for (Role role : userInfo.getRoles()) {
//                authorites.add(new SimpleGrantedAuthority(role.getRole().name()));
//            }
//
//            // 3. Send Spring Security user object to the authentication manager .
//            return new User(userInfo.getEmail(),userInfo.getPassword(),authorites);
//        }


        if (!opt.isPresent() || opt.get().getMode().name().equals("DISABLED")) {  //  If user not present throw user not found exception
            throw new UsernameNotFoundException("User Doesnt exists or disabled ");
        } else {
            // 1 . Get the user
            UserInfo userInfo = opt.get();

            // 2 and 3. Send Spring Security user object to the authentication manager .
            return new User(userInfo.getEmail(), userInfo.getPassword(),
                    userInfo.getRoles().stream()
                            .map(role -> new SimpleGrantedAuthority(role.getRole().name()))
                            .collect(Collectors.toSet())
            );
        }

    }
}
