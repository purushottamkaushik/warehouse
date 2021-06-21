package com.warehouse.service.impl;

import com.warehouse.customexception.SaleOrderNotFoundException;
import com.warehouse.model.SaleOrder;
import com.warehouse.repo.SaleOrderRepository;
import com.warehouse.service.ISaleOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SaleOrderServiceImpl implements ISaleOrderService {

    @Autowired
    private SaleOrderRepository saleOrderRepository;

    @Override
    public Integer saveSaleOrder(SaleOrder saleOrder) {
        return saleOrderRepository.save(saleOrder).getId();
    }

    @Override
    public void updateSaleOrder(SaleOrder saleOrder) {
        if(saleOrderRepository.existsById(saleOrder.getId())) {
            saleOrderRepository.save(saleOrder);
        } else {
            throw new SaleOrderNotFoundException("Sale Order with id " + saleOrder.getId()+ " Not Found");
        }

    }

    @Override
    public void deleteSaleOrder(Integer id) {
        if (saleOrderRepository.existsById(id)){
            saleOrderRepository.deleteById(id);
        } else {
            throw new SaleOrderNotFoundException("Sale Order with id " + id + " Not Found");
        }
    }

    @Override
    public SaleOrder getOneSaleOrder(Integer id) {
        Optional<SaleOrder> saleOrder = saleOrderRepository.findById(id);
        if (saleOrder.isPresent()) {
            return saleOrder.get();
        } else{
            throw new SaleOrderNotFoundException();
        }
    }

    @Override
    public List<SaleOrder> getAllSaleOrders() {
        List<SaleOrder> saleOrders = saleOrderRepository.findAll();
        if (!saleOrders.isEmpty()) {
            return saleOrders;
        } else {
            return new ArrayList<>();
        }
    }
}
