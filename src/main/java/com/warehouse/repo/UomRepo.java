package com.warehouse.repo;

import com.warehouse.model.Uom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UomRepo extends JpaRepository<Uom,Integer> {
    // For Register Operation
    @Query("SELECT count(uomModel) FROM Uom WHERE uomModel=:model")
    Integer  getUomModelCount(String model);

    // For Edit Operation
    @Query("SELECT count(uomModel) FROM Uom WHERE uomModel=:model and uomId<>:id")
    Integer  getUomModelCountForEdit(String model,Integer id);
}
