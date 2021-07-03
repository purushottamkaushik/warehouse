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


@Api("Uom Rest Controller ")
@RestController
@RequestMapping("/rest/api/v1/uom")
public class UomRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UomRestController.class);

    @Autowired
    private IUomService uomService;

    @ApiOperation("Save Unit Of Measure")
    @PostMapping("/save")
    public ResponseEntity<?> saveUom(@RequestBody Uom uom) {
        try{
            Integer id = uomService.saveUom(uom);
            return new ResponseEntity<>(id.toString() + " created Success", HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error("Could not save : {0}",e.getMessage());
        }
        return null;
    }


    @GetMapping("/one/{id}")
    public ResponseEntity<?> getOneUomById(@PathVariable Integer id) {

        try{
            Uom uom = uomService.getOneUom(id);
            return new ResponseEntity<>(uom,HttpStatus.OK);
        } catch (UomNotFoundException unfe) {
            LOGGER.error("Could not find Uom : {}" , unfe.getMessage() );
//            return new ResponseEntity<>(unfe.getMessage(),HttpStatus.OK);
            throw  unfe;
        } catch (Exception e ) {
            return  new ResponseEntity<>(e.getMessage(),HttpStatus.OK);
        }

    }

}
