package com.warehouse.service;

import com.warehouse.model.SaleOrder;

import java.util.List;

public interface ISaleOrderService {

    Integer saveSaleOrder(SaleOrder saleOrder);

    void updateSaleOrder(SaleOrder saleOrder);

    void deleteSaleOrder(Integer id);

    SaleOrder getOneSaleOrder(Integer id);

    List<SaleOrder> getAllSaleOrders();
}
