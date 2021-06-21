package com.warehouse.controller;

import com.warehouse.customexception.SaleOrderNotFoundException;
import com.warehouse.model.SaleOrder;
import com.warehouse.service.ISaleOrderService;
import com.warehouse.service.IShipmentTypeService;
import com.warehouse.service.IWhUserTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/so")
public class SaleOrderController {

    private static final Logger logger = LoggerFactory.getLogger(SaleOrderController.class);

    @Autowired
    private ISaleOrderService saleOrderService;

    @Autowired
    private IShipmentTypeService shipmentTypeService;

    @Autowired
    private IWhUserTypeService whUserTypeService;

    private void commonUI(Model model) {
        model.addAttribute("sos",shipmentTypeService.getShipmentIdAndCodeByEnable("YES"));
        model.addAttribute("wh",whUserTypeService.getWhUserIdAndCodeByType("Customer"));
    }
    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        commonUI(model);
        return "SaleOrderRegister";
    }

    @PostMapping("/save")
    public String saveSaleOrder(@ModelAttribute SaleOrder saleOrder, Model model) {
        String message = "";
        try {
            logger.info("Into sale order save method");
            Integer id =  saleOrderService.saveSaleOrder(saleOrder);
            if (id!=0) {
                message = "Sale Order with id " + id + " created successfuly";
            }
            logger.info("Exiting from sale order save method");
        } catch (Exception e) {
            e.printStackTrace();
            message = "Could not create sale Order , Please check application logs";
            logger.error("Could not save sale order : {}" ,  e.getMessage() );
        }
        model.addAttribute("message",message);
        commonUI(model);
        return "SaleOrderRegister";
    }

    @GetMapping("/all")
    public String getAllSaleOrders(Model model) {
        String message = "";
        try {
            List<SaleOrder> response  = saleOrderService.getAllSaleOrders();
            if (response.isEmpty()) {
                message = "No Sale Orders Found";
            } else{
                message = "Found " + response.size() + " Sale Orders";
            }
            model.addAttribute("list",response);
        } catch (SaleOrderNotFoundException se) {
            se.printStackTrace();
            logger.error(se.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Could not fetch sale orders  : {0} " ,e.getMessage()  );
        }
        model.addAttribute("message",message);
        return "SaleOrderData";
    }
//
//    @ResponseBody
//    public String getOneSaleOrder(@RequestParam("id") Integer id) {
//        try {
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error("Could not find sale order with id  {0}" , id);
//        }
//
//        return "SaleOrderEdit"
//    }



}
