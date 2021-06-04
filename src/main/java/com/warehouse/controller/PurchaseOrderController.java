package com.warehouse.controller;

import com.warehouse.customexception.UomNotFoundException;
import com.warehouse.model.PurchaseOrder;
import com.warehouse.model.Uom;
import com.warehouse.service.IPurchaseOrderService;
import com.warehouse.service.IUomService;
import com.warehouse.util.UomUtil;
import com.warehouse.view.UomExcelExport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import java.util.List;

@Controller
@RequestMapping("/po")
public class PurchaseOrderController {

    private final static Logger LOG = LoggerFactory.getLogger(PurchaseOrderController.class);


    @Autowired
    private IPurchaseOrderService poService;

    @Autowired
    private UomUtil util;

    @Autowired
    private ServletContext context;

    // 1. Show register page
    @GetMapping("/register")
    public String registerUom() {
        return "PurchaseOrderRegister";
    }

    //2. Save Operation
    @PostMapping("/save")
    public String savePo(@ModelAttribute PurchaseOrder purchaseOrder, Model model) {
        String message = "";
        try {
            LOG.info("Entered into Purchase Order  Save method");
            Integer id = poService.savePurchaseOrder(purchaseOrder);
            model.addAttribute("message", "Purchase Order having id " + id + " created");
            LOG.info("Exiting from Purchase Order Save method");
        } catch (Exception e) {
            LOG.error("Could not save Purchase Order: {}", e.getMessage());
            message = e.getMessage();
        }
        model.addAttribute("message",message);
        return "PurchaseOrderRegister";
    }

    // 3. Fetch all Purchase Orders
    @GetMapping("/all")
    public String getAllPurchaseOrders(Model model) {

        try {
            LOG.info("Entered into getAllPurchaseOrders method");

            List<PurchaseOrder> purchaseOrders = poService.getPurchaseOrders();
            model.addAttribute("list", purchaseOrders);
            LOG.info("Exiting from getAllPurchaseOrders method");
        } catch (Exception e) {
            LOG.error("Could not fetch Purchase Orders: {}", e.getMessage());
        }
        return "PurchaseOrderData";

    }


}
