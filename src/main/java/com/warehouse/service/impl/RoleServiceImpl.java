package com.warehouse.service.impl;

import com.warehouse.model.Role;
import com.warehouse.repo.RoleRepository;
import com.warehouse.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RoleRepository repo;

    @Override
    public Map<Integer, String> getRolesMap() {
        List<Role> rolesList = repo.findAll();
        Map<Integer, String> rolesMap = new HashMap<Integer, String>();
        for (Role role : rolesList) {
            rolesMap.put(role.getId(), role.getRole().name());
        }
        return rolesMap;
    }
}
