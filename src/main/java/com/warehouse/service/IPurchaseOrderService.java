package com.warehouse.service;

import com.warehouse.customexception.UomNotFoundException;
import com.warehouse.model.Document;
import com.warehouse.model.PurchaseDtl;
import com.warehouse.model.PurchaseOrder;
import com.warehouse.model.Uom;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IPurchaseOrderService {
    //

    Integer savePurchaseOrder(PurchaseOrder purchaseOrder);

    PurchaseOrder getOnePurchaseOrder(Integer id);

    List<PurchaseOrder> getPurchaseOrders();

//  void updateUom(PurchaseOrder purchaseOrder);

    boolean isPurchaseOrderCodeExists(String orderCode);

    boolean isPurchaseOrderCodeExistsForEdit(String orderCode, Integer id);

//  Map<Integer,String> getPurchaseOrderIdAndModel();

    // ================Screen 2 Operations =====

    Integer savePurchaseDetail(PurchaseDtl purchaseDtl);

    List<PurchaseDtl> getPurchaseDetailsByPoId(Integer id);

    void deletePurchaseDetailById(Integer purchaseDetailId);

    String getCurrentStatusByPoId(Integer poId);

    void updateCurrentStatusByPoId(Integer poId, String status);

    Integer getPurchaseDetailCountByPoId(Integer poId);

    Optional<PurchaseDtl> getPurchaseDetailsByPoIdAndPartId(Integer poId, Integer partId);

    void updatePurchaseDetailQtyById(Integer newValue, Integer id);

    Map<Integer,String> getIdAndOrderCodeByStatus(String status);

}
