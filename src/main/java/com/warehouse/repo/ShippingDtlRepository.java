package com.warehouse.repo;


import com.warehouse.model.ShippingDtl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface ShippingDtlRepository extends JpaRepository<ShippingDtl ,Integer> {



    @Modifying // performing update operation
    @Query("UPDATE ShippingDtl set status=:status where id=:id")
    void updateStatusById(Integer id , String status);
}
