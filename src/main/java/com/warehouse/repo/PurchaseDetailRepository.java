package com.warehouse.repo;

import com.warehouse.model.PurchaseDtl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PurchaseDetailRepository extends JpaRepository<PurchaseDtl,Integer> {


    @Query("SELECT pdtl from PurchaseDtl  pdtl JOIN pdtl.po as purchaseOrder where purchaseOrder.id=:poId")
    public List<PurchaseDtl> getPurchaseDetailByPoId(Integer poId);

    @Query("SELECT count(pdtl) from PurchaseDtl  pdtl JOIN pdtl.po as purchaseOrder where purchaseOrder.id=:poId")
    public Integer getPurchaseDetailCountByPoId(Integer poId);

    // SELECT PurchaseDetail based on Part and PO includes JOINS using three table
    @Query("SELECT dtl FROM PurchaseDtl dtl " +
            " JOIN " +
            "dtl.part as part " +
            " JOIN " +
            "dtl.po as po " +
            "where part.id=:partId AND po.id=:poId")
    Optional<PurchaseDtl> getPurchaseDetailsByPoIdAndPartId(Integer poId, Integer partId);


    @Modifying // For Telling its a Non Select Operation to the @Query Annotation which takes by default a select query
    @Query("update PurchaseDtl  set qty=qty + :newValue where id=:id")
    void updatePurchaseDetailQtyById(Integer newValue , Integer id);

}



