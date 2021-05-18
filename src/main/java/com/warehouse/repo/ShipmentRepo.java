package com.warehouse.repo;

import com.warehouse.model.ShipmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentRepo extends JpaRepository<ShipmentType,Integer> {


    // Register page
    @Query("SELECT count(shipmentCode) FROM ShipmentType where shipmentCode=:code")
    Integer checkShipmentCodeExist(String code);

    // Edit page
    // select count(shipment_code) from shipment_type where shipment_code='HELLO' and shipment_id=1
    @Query("SELECT count(shipmentCode) FROM ShipmentType where shipmentCode=:code and shipmentId<>:id")
    Integer checkShipmentCodeExist(String code,Integer id);

}
