package com.warehouse.service.impl;

import com.warehouse.customexception.OrderMethodNotFoundException;
import com.warehouse.model.OrderMethod;
import com.warehouse.repo.OrderMethodRepo;
import com.warehouse.service.IOrderMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderMethodServiceImpl implements IOrderMethodService {

    @Autowired
    private OrderMethodRepo repo;

    @Override
    public Integer saveOrderMethod(OrderMethod orderMethod) throws OrderMethodNotFoundException, Exception {
        return repo.save(orderMethod).getId();
    }

    @Override
    public void updateOrderMethod(OrderMethod orderMethod) {
        repo.save(orderMethod);
    }

    @Override
    public OrderMethod getOneOrderMethod(Integer id) throws OrderMethodNotFoundException {
        return repo.findById(id).orElseThrow(() -> {
            throw new OrderMethodNotFoundException("Order method with '" + id + "' not found");
        });
    }

    @Override
    public List<OrderMethod> getAllOrderMethod() {
        return repo.findAll();
    }

    @Override
    public void deleteOrderMethod(Integer id) {
        if (id != null && getOneOrderMethod(id) != null) {
            repo.deleteById(id);
        }
    }

    @Override
    public boolean isOrderCodeExists(String orderCode) {
        return repo.isOrderCodeExist(orderCode) > 0;
    }

    @Override
    public boolean isOrderCodeExistsForEdit(String orderCode, Integer id) {
        return repo.isOrderCodeExist(orderCode, id) > 0;
    }

    @Override
    public List<Object[]> getOrderModeCount() {
        return repo.shipmentTypeModeCount();
    }
}
