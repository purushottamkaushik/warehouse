package com.warehouse.repo;

import com.warehouse.consts.RoleType;
import com.warehouse.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role , Integer> {

    Boolean existsByRole(RoleType type);
}
