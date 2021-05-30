package com.warehouse.service;

import com.warehouse.customexception.OrderMethodNotFoundException;
import com.warehouse.customexception.PartNotFoundException;
import com.warehouse.model.OrderMethod;
import com.warehouse.model.Part;

import java.util.List;

public interface IPartService {

    Integer savePart(Part part) throws PartNotFoundException, Exception;

    void updatePart(Part part);

    Part getOnePart(Integer id) throws PartNotFoundException;

    List<Part> getAllParts();

//    void deleteOrderMethod(Integer id);
//
//    boolean isOrderCodeExists(String orderCode);
//
//    boolean isOrderCodeExists(String orderCode, Integer id);
//
//    List<Object[]> getOrderModeCount();


}
