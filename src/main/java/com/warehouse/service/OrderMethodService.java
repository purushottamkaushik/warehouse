package com.warehouse.service;

import com.warehouse.customexception.OrderMethodNotFoundException;
import com.warehouse.model.OrderMethod;

import java.util.List;

public interface OrderMethodService {

    Integer saveOrderMethod(OrderMethod orderMethod) throws OrderMethodNotFoundException,Exception;

    void updateOrderMethod(OrderMethod orderMethod);

    OrderMethod getOneOrderMethod(Integer id) throws OrderMethodNotFoundException;

    List<OrderMethod> getAllOrderMethod();

    void deleteOrderMethod(Integer id);

    boolean isOrderCodeExists(String orderCode);

    boolean isOrderCodeExistsForEdit(String orderCode, Integer id);

}
