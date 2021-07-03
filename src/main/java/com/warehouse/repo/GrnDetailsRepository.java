package com.warehouse.repo;

import com.warehouse.model.GrnDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface GrnDetailsRepository extends JpaRepository<GrnDetail,Integer> {

    @Modifying
    @Query("UPDATE GrnDetail SET status=:status WHERE id=:id")
    void updateGrnDetailsById(Integer id , String status);
}
