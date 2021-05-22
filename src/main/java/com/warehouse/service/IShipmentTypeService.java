package com.warehouse.service;

import com.warehouse.model.ShipmentType;

import java.util.List;

public interface IShipmentTypeService {
    Integer saveShipmentType(ShipmentType shipmentType);

    List<ShipmentType> getAllShipmentType();

    Boolean deleteShipType(Integer id) throws Exception;

    ShipmentType getOneShipmentType(Integer id) throws Exception;

    Integer updateShipmentType(ShipmentType shipmentType);

    Boolean isShipmentCodeExists(String code);

    Boolean isShipmentCodeExists(String code, Integer id);


    List<Object[]> getShipmentModeAndCount();

}
