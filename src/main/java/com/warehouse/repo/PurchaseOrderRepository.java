package com.warehouse.repo;

import com.warehouse.model.Document;
import com.warehouse.model.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;


public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Integer> {

    // For register we dont want the orderCode to be duplicate
    @Query("SELECT count (orderCode)  FROM PurchaseOrder WHERE orderCode=:orderCode")
    Integer getOrderCodeCount(String orderCode);

    // FOr edit operations
    @Query("SELECT count(orderCode) FROM PurchaseOrder WHERE orderCode=:orderCode AND id<>:id")
    Integer getOrderCodeCountForEdit(String orderCode , Integer id);

    @Query("SELECT status from PurchaseOrder where id=:poId")
    String getCurrentStatusByPoId(Integer poId);

    @Modifying
    @Transactional
    @Query("update PurchaseOrder set status=:status where id=:poId")
    void updatePoStatus(Integer poId, String status);

}
