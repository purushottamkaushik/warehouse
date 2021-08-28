package com.warehouse.controller.rest;

import com.warehouse.customexception.ShipmentTypeNotFoundException;
import com.warehouse.model.ShipmentType;
import com.warehouse.service.IShipmentTypeService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("Api for ShipmentType Operation")
@RestController
@RequestMapping("/rest/api/v1/st")
public class ShipmentTypeRestController {

   @Autowired
   private IShipmentTypeService shipmentTypeService;

   private static final Logger LOGGER = LoggerFactory.getLogger(ShipmentTypeRestController.class);

   @PostMapping("/create")
   public ResponseEntity<String> saveShipment(@RequestBody ShipmentType shipmentType) {
      Integer id = null;
      try {
         LOGGER.info("REST : Entered into save shipment method ");
         id = shipmentTypeService.saveShipmentType(shipmentType);
         LOGGER.debug("REST : Exiting from save shipment method ");
      } catch (Exception e) {
         LOGGER.error("Could not save shipment : { }", e.getMessage());
      }
      return ResponseEntity.ok("Shipment with id " + id + " created successfully ");
   }

   @GetMapping("/one/{id}")
   public ResponseEntity<ShipmentType> getOneShipmentType(@PathVariable Integer id) {
      ShipmentType shipmentType = null;
      try {
         LOGGER.info("REST : Entered into getOneShipmentType method ");
         shipmentType = shipmentTypeService.getOneShipmentType(id);

      } catch (ShipmentTypeNotFoundException snfe) {
         LOGGER.error("Shipment Not found ", snfe.getMessage());
         throw snfe;
      } catch (Exception e) {
         e.printStackTrace();
         LOGGER.error("Could not get Shipment Type  {} ", e.getMessage());
      }
      return ResponseEntity.ok(shipmentType);
   }

   @GetMapping("/all")
   public ResponseEntity<?> getAllShipmentTypes() {
      List<ShipmentType> shipmentTypeList = null;
      String message = "";
      try {
         shipmentTypeList = shipmentTypeService.getAllShipmentType();
         if (shipmentTypeList.isEmpty()) {
            message = "No Shipment Types Found";
            return new ResponseEntity<>(message, HttpStatus.NO_CONTENT);
         }
         return new ResponseEntity<>(shipmentTypeList, HttpStatus.OK);
      } catch (ShipmentTypeNotFoundException shipmentTypeNotFoundException) {
         LOGGER.error("No Shipments Found ");
         throw shipmentTypeNotFoundException;
      } catch (Exception e) {
         LOGGER.error("Could not fetch Shipment Types : {} ", e.getMessage());
         message = "Something went wrong please check application logs ";
      }
      System.out.println("Testing commit");
      return ResponseEntity.ok(message);
   }

   @PutMapping("/update")
   public String updateShipmentType(@RequestBody ShipmentType shipmentType) {
      Integer shipmentId = shipmentType.getId();
      String message = "";
      try {
         LOGGER.info("REST : Entered into shipment Type Update Shipping ");
         shipmentTypeService.updateShipmentType(shipmentType);
         message = "Shipment with id " + shipmentId + "  updated Successfully";
         LOGGER.debug("REST : Exiting from Shipment Type Update Method ");
      } catch (ShipmentTypeNotFoundException shipmentTypeNotFoundException) {
         LOGGER.error("Shipment Type not Found with id : {} ", shipmentType.getId());
         message = "Could not found shipping with id " + shipmentId;
         throw shipmentTypeNotFoundException;
      } catch (Exception e) {
         LOGGER.error("Unable to update Shipment Type : {} ", e.getMessage());
         message = "Something went wrong , Please check application logs ";
      }
      return message;
   }
   @DeleteMapping("/delete/{id}")
   public String deleteShipmentType(@PathVariable Integer id) {
      String message = "";
      try {
         LOGGER.error("REST : Entered into Shipment Type Delte methoid ");
         shipmentTypeService.deleteShipType(id);
         message = "Shipment with id  " +id + " deleted Successfully" ;
          LOGGER.debug("REST : Exiting from delete shipmeent Type method ");
      } catch (ShipmentTypeNotFoundException snfe) {
         LOGGER.error("REST  : Shipment Type with id {} Not Found ... " , id);
         throw snfe;
      } catch (Exception e ) {
         LOGGER.error("REST : Could not Delete Shipping ");
         message = "Something went wrong , please check application logs ";
      }
      return message ;
   }

}
