package com.warehouse.controller;

import com.warehouse.consts.SaleOrderStatus;
import com.warehouse.model.SaleOrder;
import com.warehouse.model.Shipping;
import com.warehouse.service.ISaleOrderService;
import com.warehouse.service.IShippingService;
import org.dom4j.rule.Mode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/sp")
public class ShippingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShippingController.class);

    @Autowired
    private IShippingService shippingService;


    @Autowired
    private ISaleOrderService saleOrderService;

    private void commonUi(Model model) {
        model.addAttribute("soc" ,  saleOrderService.getSaleOrderIdAndCodeByStatus(SaleOrderStatus.INVOICED.name()));
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model){
        commonUi(model);
        return "ShippingRegisterPage";
    }

    @PostMapping("/save")
    public String saveShipping(@ModelAttribute Shipping shipping , Model model) {
        try {
            LOGGER.info("Entereed into Shipping Save method ");
            Integer id = shippingService.saveShipping(shipping);
            String message = "Shipping with id " + id + " created Successfully" ;
            model.addAttribute("message",message);
            saleOrderService.updateSaleOrderStatus(shipping.getSo().getId(), SaleOrderStatus.SHIPPED.name());
            LOGGER.debug("Exiting from Shipping Save method " );
        } catch (Exception e) {
            LOGGER.error("Could not execute Shiipping method  : {} " + e.getMessage() );
        }
        commonUi(model);
        return "ShippingRegisterPage";
    }

    @GetMapping("/all")
    public String getAllShippings(Model model) {
        try {
            LOGGER.info("Entered into Shipping Module GET All shippings method");
            List<Shipping> shippingList = shippingService.getAllShippings();
            model.addAttribute("list",shippingList);
            LOGGER.debug("Exiting from getAllShipping method ");
        } catch (Exception e ) {
            LOGGER.error("Could not fetch shipping : {} " , e.getMessage());
        }
        return "ShippingDataPage";
    }






}
