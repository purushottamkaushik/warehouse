package com.warehouse.service;

import com.warehouse.customexception.OrderMethodNotFoundException;
import com.warehouse.model.OrderMethod;

import java.util.List;
import java.util.Map;

public interface IOrderMethodService {

    Integer saveOrderMethod(OrderMethod orderMethod) throws OrderMethodNotFoundException, Exception;

    void updateOrderMethod(OrderMethod orderMethod);

    OrderMethod getOneOrderMethod(Integer id) throws OrderMethodNotFoundException;

    List<OrderMethod> getAllOrderMethod();

    void deleteOrderMethod(Integer id);

    boolean isOrderCodeExists(String orderCode);

    boolean isOrderCodeExists(String orderCode, Integer id);

    List<Object[]> getOrderModeCount();

    Map<Integer,String> getAllOrderCodes();
}
