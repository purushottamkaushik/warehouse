package com.warehouse.runners;

import com.warehouse.consts.UserMode;
import com.warehouse.model.UserInfo;
import com.warehouse.repo.RoleRepository;
import com.warehouse.repo.UserInfoRepository;
import com.warehouse.service.IUserInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserInsertRunner implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private IUserInfoService userInfoService ;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserInsertRunner.class);

    @Override
    public void run(String... args) throws Exception {
        LOGGER.info("Entered into MASTER account creation runner ");
        if (!userInfoRepository.existsByEmail("purushottamkaushik96@gmail.com").booleanValue()) {
            UserInfo userInfo = new UserInfo();
            userInfo.setEmail("purushottamkaushik96@gmail.com");
            userInfo.setMode(UserMode.ENABLED);
            userInfo.setName("kaushik");
            userInfo.setPassword("hello");
            userInfo.setRoles(roleRepository.findAll().stream().collect(Collectors.toSet()));
            userInfoService.saveUser(userInfo);
        }
        LOGGER.info("MASTER ACCOUNT CREATED AND READY TO USE ");
    }
}
