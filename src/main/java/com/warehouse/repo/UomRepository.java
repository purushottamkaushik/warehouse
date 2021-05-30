package com.warehouse.repo;

import com.warehouse.model.Uom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UomRepository extends JpaRepository<Uom, Integer> {
    // For Register Operation
    @Query("SELECT count(uomModel) FROM Uom WHERE uomModel=:model")
    Integer getUomModelCount(String model);

    // For Edit Operation
    @Query("SELECT count(uomModel) FROM Uom WHERE uomModel=:model and id<>:id")
    Integer getUomModelCountForEdit(String model, Integer id);

    // For chart based On uomType
    @Query("SELECT uomType ,count(uomType) FROM Uom GROUP BY uomType")
    List<Object[]> getUomTypeAndCount();

    @Query("SELECT id,uomModel FROM Uom")
    List<Object[]> getUomIdAndModel();
}
