package com.warehouse.repo;

import com.warehouse.model.OrderMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderMethodRepo extends JpaRepository<OrderMethod, Integer> {

    // For Register purpose
    @Query("SELECT count(orderCode) FROM OrderMethod WHERE orderCode<>:orderCode")
    Integer isOrderCodeExist(String orderCode);

    // For Edit Operation
    @Query("SELECT count(orderCode) FROM OrderMethod WHERE orderCode=:orderCode and id<>:id")
    Integer isOrderCodeExist(String orderCode, Integer id);

    // For chart
    @Query("SELECT orderMode , count(orderMode) FROM OrderMethod GROUP BY orderMode")
    List<Object[]> shipmentTypeModeCount();

}
