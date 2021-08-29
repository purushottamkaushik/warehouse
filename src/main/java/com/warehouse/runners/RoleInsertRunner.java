package com.warehouse.runners;

import com.warehouse.consts.RoleType;
import com.warehouse.model.Role;
import com.warehouse.repo.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleInsertRunner implements CommandLineRunner {

    @Autowired
    private RoleRepository repo;

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleInsertRunner.class);
    @Override
    public void run(String... args) throws Exception {
        System.out.println("Insert into RoleRunner");

        RoleType[] roleTypes = RoleType.values();

        for (RoleType rt : roleTypes) {
            System.out.println("Role Type  : " + rt);
            if (!repo.existsByRole(rt)) {
                Role role = new Role();
                role.setRole(rt);
                repo.save(role);
                LOGGER.info("Role {}  Addedd Successfully" , rt);

            }
        }


//        if (!repo.existsByRole(RoleType.ADMIN)) {
//            Role role = new Role();
//            role.setRole(RoleType.ADMIN);
//            repo.save(role);
//            LOGGER.info("ADMIN Role Entered into the DB");
//        }
//
//
//        if (!repo.existsByRole(RoleType.APPUSER)) {
//            Role role = new Role();
//            role.setRole(RoleType.APPUSER);
//            repo.save(role);
//            LOGGER.info("APPUSER Role Entered into the DB");
//
//        }
//


        LOGGER.debug("Roles Inserted into db Successfully");
    }
}
