package com.warehouse.repo;

import com.warehouse.model.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PartRepository extends JpaRepository<Part,Integer> {


    @Query("SELECT id,partCode from Part ")
    public List<Object[]> getPartIdAndCode();
}
