package com.warehouse.util;

import com.warehouse.model.Role;

import java.util.Set;
import java.util.stream.Collectors;

public interface UserInfoUtils {

    public static Set<String> getUserRolesInString(Set<Role> roles ) {
        return roles.stream().map(role -> role.getRole().name()).collect(Collectors.toSet());
    }
}
