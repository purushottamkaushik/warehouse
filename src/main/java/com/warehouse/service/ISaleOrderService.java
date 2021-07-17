package com.warehouse.service;

import com.warehouse.model.SaleDtl;
import com.warehouse.model.SaleOrder;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ISaleOrderService {

    Integer saveSaleOrder(SaleOrder saleOrder);

    void updateSaleOrder(SaleOrder saleOrder);

    void deleteSaleOrder(Integer id);



    // Screen 2 Operations


    SaleOrder getOneSaleOrder(Integer id);

    List<SaleOrder> getAllSaleOrders();

    String getCurrentStatusById(Integer id);

    Optional<SaleDtl> getSaleOrderBySaleIdAndPartId(Integer soId, Integer partId );


    void updateSaleOrderStatus(Integer id ,String status);


    void updateSaleDetail(Integer id, Integer qty);

    Integer saveSaleDetail(SaleDtl saleDtl);

    List<SaleDtl> getSaleOrderDetailsBySoId(Integer id);

    public Integer isSaleDetailsExistBySoId(Integer id);

    void deleteSaleDtlById(Integer dtlId);

    void updateSaleQuantity(Integer dtlId, Integer newValue );

    Map<Integer,String > getSaleOrderIdAndCodeByStatus(String status);
}
