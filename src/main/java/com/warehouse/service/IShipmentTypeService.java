package com.warehouse.service;

import com.warehouse.model.ShipmentType;

import java.util.List;
import java.util.Map;

public interface IShipmentTypeService {
    Integer saveShipmentType(ShipmentType shipmentType);

    List<ShipmentType> getAllShipmentType();

    void deleteShipType(Integer id) throws Exception;

    ShipmentType getOneShipmentType(Integer id) throws Exception;

    void updateShipmentType(ShipmentType shipmentType);

    Boolean isShipmentCodeExists(String code) throws Exception;

    Boolean isShipmentCodeExists(String code, Integer id);


    List<Object[]> getShipmentModeAndCount();

    Map<Integer,String> getShipmentIdAndCodeByEnable(String enable);

}
