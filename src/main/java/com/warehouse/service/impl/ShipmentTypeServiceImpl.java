package com.warehouse.service.impl;

import com.warehouse.model.ShipmentType;
import com.warehouse.repo.ShipmentRepo;
import com.warehouse.service.IShipmentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShipmentTypeServiceImpl implements IShipmentTypeService {

    @Autowired
    private ShipmentRepo repo;

    @Override
    public Integer saveShipmentType(ShipmentType shipmentType) {
        ShipmentType shipmentType1 = repo.save(shipmentType);
        return shipmentType1.getId();
    }

    @Override
    public List<ShipmentType> getAllShipmentType() {
        return repo.findAll();
    }

    @Override
    public Boolean deleteShipType(Integer id) throws Exception {
        Optional<ShipmentType> shipmentType = repo.findById(id);
        if (shipmentType.isPresent()) {
            repo.deleteById(id);
            return true;
        } else {
            throw new Exception("Shipment with shipment id " + id + " doesnt exist");
        }

    }

    @Override
    public ShipmentType getOneShipmentType(Integer id) throws Exception {
        Optional<ShipmentType> shipmentType = repo.findById(id);
        if (shipmentType.isPresent()) {
            return shipmentType.get();
        } else {
            throw new Exception("Shipment with " + id + " doesnt exist ");
        }
    }

    @Override
    public Integer updateShipmentType(ShipmentType shipmentType) {
        return repo.save(shipmentType).getId();
    }

    @Override
    public Boolean isShipmentCodeExists(String code) {

        try {
            return repo.checkShipmentCodeExist(code) > 0;
        } catch (Exception e) {

        }
        return null;
    }

    @Override
    public Boolean isShipmentCodeExists(String code, Integer id) {
        try {
            return repo.checkShipmentCodeExist(code, id) > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Object[]> getShipmentModeAndCount() {
        return repo.getShipmentModeAndCount();
    }
}
