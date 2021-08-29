package com.warehouse.controller.rest;

import com.warehouse.customexception.UomNotFoundException;
import com.warehouse.model.Uom;
import com.warehouse.service.IUomService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Api("Uom Rest Controller ")
@RestController
@RequestMapping("/rest/api/v1/uom")
public class UomRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UomRestController.class);

    @Autowired
    private IUomService uomService;


    // 1 . Save Uom
    @ApiOperation("Save Unit Of Measure")
    @PostMapping("/create")
    public ResponseEntity<?> saveUom(@RequestBody Uom uom) {

        try{
            Integer id = uomService.saveUom(uom);
            return new ResponseEntity<>("Uom with id " + id.toString() + " created Success", HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Could not save : {0}",e.getMessage());
            return new ResponseEntity<>("Could not save UOM" , HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    // 2. Find One Uom
    @GetMapping("/find/{id}")
    @ApiOperation("Find one UOM by ID")
    public ResponseEntity<?> getOneUomById(@PathVariable Integer id) {
        Uom uom = null;
        try {
            LOGGER.info("Into get One Uom By Id Method");
            uom = uomService.getOneUom(id);
            LOGGER.debug("Exiting from getOneUOM method : REST");
        } catch (UomNotFoundException unfe) {
            LOGGER.error("Could not find Uom : {}", unfe.getMessage());
//            return new ResponseEntity<>(unfe.getMessage(),HttpStatus.OK);
            throw unfe;
        } catch (Exception e) {
            LOGGER.error("Could not find UOM by ID : ", e.getMessage());
            return new ResponseEntity<>("some problem occurred please check application logs ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return  ResponseEntity.ok(uom);
    }


    // 3. Get all UOM
    @GetMapping("/all")
    public ResponseEntity<?> getAllUoms() {
        List<Uom> uoms = new ArrayList<>();
        try {
            LOGGER.info("Entered into GET ALL UOMS method ");
            uoms = uomService.getAllUoms();

            LOGGER.debug("Exiting from ");
        } catch (UomNotFoundException unfe) {
            LOGGER.error("Uom not found ", unfe.getMessage());
            throw unfe;
        } catch (Exception e) {
            LOGGER.error("Could not get Uoms : ", e.getMessage());
            return new ResponseEntity<>("some problem occurred please check application logs ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(uoms,HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> removeUom(@PathVariable Integer id ) {
        String message = "" ;
        try {
            LOGGER.error("Entered into UOM delete method : REST ");
            uomService.deleteUom(id);
            message = "UOM with id " + id + " deleted Successfully" ;
            LOGGER.debug("Exiting from UOM delete methiod : REST ");
        } catch (UomNotFoundException uomNotFoundException) {
            LOGGER.error("UOM not found ");
            throw uomNotFoundException;
        } catch (Exception e) {
            LOGGER.error("Could not DELETE Uom : " ,e.getMessage());
            return new ResponseEntity<>("Something went wrong please check application logs",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(message);
    }


    @PutMapping("/update")
    public ResponseEntity<String> updateUom(@RequestBody Uom uom  ) {
        String message = "" ;
        try {
            LOGGER.error("Entered into UOM Update method : REST ");
            uomService.updateUom(uom);
            message = "UOM with id " + uom.getId() + " updated  Successfully";
            LOGGER.debug("Exiting from UOM Update  methiod : REST ");
        } catch (UomNotFoundException uomNotFoundException) {
            LOGGER.error("UOM not found ");
            throw uomNotFoundException;
        } catch (Exception e) {
            LOGGER.error("Could not DELETE Uom : ", e.getMessage());
            return new ResponseEntity<>("Something went wrong please check application logs", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok(message);
    }
}
