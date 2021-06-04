package com.warehouse.service;

import com.warehouse.customexception.UomNotFoundException;
import com.warehouse.model.Document;
import com.warehouse.model.PurchaseOrder;
import com.warehouse.model.Uom;

import java.util.List;
import java.util.Map;

public interface IPurchaseOrderService {
  //

  Integer savePurchaseOrder(PurchaseOrder purchaseOrder);

  PurchaseOrder getOnePurchaseOrder(Integer id) ;

  List<PurchaseOrder> getPurchaseOrders();

//  void updateUom(PurchaseOrder purchaseOrder);

  boolean isPurchaseOrderCodeExists(String orderCode);

  boolean isPurchaseOrderCodeExistsForEdit(String orderCode, Integer id);

//  Map<Integer,String> getPurchaseOrderIdAndModel();

}
