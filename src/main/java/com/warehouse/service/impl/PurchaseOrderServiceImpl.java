package com.warehouse.service.impl;

import com.warehouse.customexception.UomNotFoundException;
import com.warehouse.model.PurchaseOrder;
import com.warehouse.model.Uom;
import com.warehouse.repo.PurchaseOrderRepository;
import com.warehouse.repo.UomRepository;
import com.warehouse.service.IPurchaseOrderService;
import com.warehouse.service.IUomService;
import com.warehouse.util.MyAppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PurchaseOrderServiceImpl implements IPurchaseOrderService {

    @Autowired
    private PurchaseOrderRepository repo;


    @Override
    public Integer savePurchaseOrder(PurchaseOrder purchaseOrder) {
      return   repo.save(purchaseOrder).getId();
    }

    @Override
    public PurchaseOrder getOnePurchaseOrder(Integer id) {
        return repo.findById(id).orElseThrow(()->new RuntimeException("Po with id '"+id+"' not foun"));
    }

    @Override
    public List<PurchaseOrder> getPurchaseOrders() {
        return repo.findAll();
    }

    @Override
    public boolean isPurchaseOrderCodeExists(String orderCode) {
        return repo.getOrderCodeCount(orderCode) > 0 ;
    }

    @Override
    public boolean isPurchaseOrderCodeExistsForEdit(String orderCode, Integer id) {
        return repo.getOrderCodeCountForEdit(orderCode,id) > 0 ;
    }
}
