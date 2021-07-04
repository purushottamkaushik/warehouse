package com.warehouse.repo;

import com.warehouse.model.SaleOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SaleOrderRepository extends JpaRepository<SaleOrder, Integer> {

    // TODO
    // For ajax calls for register and edit operations


    @Query("SELECT status FROM SaleOrder WHERE id=:id")
    String getCurrentStatusById(Integer id);

    @Modifying
    @Query("update SaleOrder set status=:status WHERE id=:id")
    void updateStatusById(Integer id, String status);
}
