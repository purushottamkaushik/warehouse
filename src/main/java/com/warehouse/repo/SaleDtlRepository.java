package com.warehouse.repo;

import com.warehouse.model.SaleDtl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SaleDtlRepository extends JpaRepository<SaleDtl , Integer> {

    @Query("SELECT  saledtl  FROM SaleDtl saledtl " +
            " JOIN " +
            "saledtl.part as part " +
            " JOIN " +
            " saledtl.so as so " +
            " where so.id=:soId AND part.id=:partId")
    Optional<SaleDtl> getSaleOrderBySaleIdAndPartId(Integer soId, Integer partId );

    @Modifying
    @Query("UPDATE SaleDtl SET qty=qty +:newValue WHERE id=:id")
    void updateDetailQuantityById(Integer id, Integer newValue);
}
