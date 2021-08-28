package com.warehouse.service.impl;

import com.warehouse.customexception.ShipmentTypeNotFoundException;
import com.warehouse.customexception.ShippingNotFoundException;
import com.warehouse.model.ShipmentType;
import com.warehouse.repo.ShipmentTypeRepository;
import com.warehouse.service.IShipmentTypeService;
import com.warehouse.util.MyAppUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ShipmentTypeServiceImpl implements IShipmentTypeService {

    @Autowired
    private ShipmentTypeRepository repo;

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
    public void deleteShipType(Integer id) throws Exception {
        Optional<ShipmentType> shipmentType = repo.findById(id);
        if (shipmentType.isPresent()) {
            repo.deleteById(id);
        } else {
            throw new ShipmentTypeNotFoundException("Shipment with shipment id " + id + " doesnt exist");
        }

    }

    @Override
    public ShipmentType getOneShipmentType(Integer id) throws Exception {
        Optional<ShipmentType> shipmentType = repo.findById(id);
        if (shipmentType.isPresent()) {
            return shipmentType.get();
        } else {
            throw new ShipmentTypeNotFoundException("Shipment with " + id + " doesnt exist ");
        }
    }

    @Override
    public void updateShipmentType(ShipmentType shipmentType) {
        Integer id = shipmentType.getId();
        if (!repo.existsById(id) || id == null ) {
            throw new ShipmentTypeNotFoundException(
                    "Shipment with " + (id!=null?id.toString():"0") + " not exist for update "
            );
        }else {
            repo.save(shipmentType);
        }
    }

    @Override
    public Boolean isShipmentCodeExists(String code) {

        return repo.checkShipmentCodeExist(code) > 0;
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

    @Override
    public Map<Integer,String> getShipmentIdAndCodeByEnable(String enable) {
            return MyAppUtil.convertListToMap(repo.getShipmentIdAndCodeByEnable(enable));
    }
}
