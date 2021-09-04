package com.warehouse.repo;

import com.warehouse.consts.UserMode;
import com.warehouse.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {

    Optional<UserInfo> findByEmail(String email);

    Boolean existsByEmail(String email);

    @Modifying
    @Query("update  UserInfo set mode=:status where id=:id")
    void updateUserStatus(Integer id , UserMode status );
}
