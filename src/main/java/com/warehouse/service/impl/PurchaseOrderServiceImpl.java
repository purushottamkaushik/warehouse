package com.warehouse.service.impl;

import com.warehouse.customexception.UomNotFoundException;
import com.warehouse.model.PurchaseDtl;
import com.warehouse.model.PurchaseOrder;
import com.warehouse.model.Uom;
import com.warehouse.repo.PurchaseDetailRepository;
import com.warehouse.repo.PurchaseOrderRepository;
import com.warehouse.repo.UomRepository;
import com.warehouse.service.IPurchaseOrderService;
import com.warehouse.service.IUomService;
import com.warehouse.util.MyAppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class PurchaseOrderServiceImpl implements IPurchaseOrderService {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private PurchaseDetailRepository purchaseDetailRepository;


    @Override
    public Integer savePurchaseOrder(PurchaseOrder purchaseOrder) {
      return   purchaseOrderRepository.save(purchaseOrder).getId();
    }

    @Override
    public PurchaseOrder getOnePurchaseOrder(Integer id) {
        return purchaseOrderRepository.findById(id).orElseThrow(()->new RuntimeException("Po with id '"+id+"' not foun"));
    }

    @Override
    public List<PurchaseOrder> getPurchaseOrders() {
        return purchaseOrderRepository.findAll();
    }

    @Override
    public boolean isPurchaseOrderCodeExists(String orderCode) {
        return purchaseOrderRepository.getOrderCodeCount(orderCode) > 0 ;
    }

    @Override
    public boolean isPurchaseOrderCodeExistsForEdit(String orderCode, Integer id) {
        return purchaseOrderRepository.getOrderCodeCountForEdit(orderCode,id) > 0 ;
    }

    @Override
    public Integer savePurchaseDetail(PurchaseDtl purchaseDtl) {
        return purchaseDetailRepository.save(purchaseDtl).getId();
    }

    @Override
    public List<PurchaseDtl> getPurchaseDetailsByPoId(Integer id) {
        return purchaseDetailRepository.getPurchaseDetailByPoId(id);
    }

    @Override
    public void deletePurchaseDetailById(Integer purchaseDetailId) {
        if (purchaseDetailRepository.existsById(purchaseDetailId)) {
            purchaseDetailRepository.deleteById(purchaseDetailId);
        }
    }

    @Override
    public String getCurrentStatusByPoId(Integer poId) {
        return purchaseOrderRepository.getCurrentStatusByPoId(poId);
    }

    @Override
    @Transactional //  For manual Update operation extra annotation need to be added
    public void updateCurrentStatusByPoId(Integer poId, String status) {
      purchaseOrderRepository.updatePoStatus(poId, status);
    }

    @Override
    public Integer getPurchaseDetailCountByPoId(Integer poId) {
        return purchaseDetailRepository.getPurchaseDetailCountByPoId(poId);
    }

    @Override
    public Optional<PurchaseDtl> getPurchaseDetailsByPoIdAndPartId(Integer poId, Integer partId) {
        return purchaseDetailRepository.getPurchaseDetailsByPoIdAndPartId(poId,partId);
    }

    @Override
    @Transactional // For Non SELECT Operations Add to the Service Layer method
    public void updatePurchaseDetailQtyById(Integer newValue, Integer id) {
        purchaseDetailRepository.updatePurchaseDetailQtyById(newValue, id);
    }

    @Override
    public Map<Integer, String> getIdAndOrderCodeByStatus(String status) {
        List<Object[]> poList =purchaseOrderRepository.getIdAndOrderCodeByStatus(status);
        return MyAppUtil.convertListToMap(poList);
    }


}
