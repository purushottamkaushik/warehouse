package com.warehouse.controller.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/api/v1/st")
public class ShipmentTypeRestController {

    @GetMapping("/hello")
    public String getSt(){
        return "Hello From rest";
    }
}
