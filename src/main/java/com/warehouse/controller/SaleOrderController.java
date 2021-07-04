package com.warehouse.controller;

import com.warehouse.consts.SaleOrderStatus;
import com.warehouse.customexception.SaleOrderNotFoundException;
import com.warehouse.model.SaleDtl;
import com.warehouse.model.SaleOrder;
import com.warehouse.service.IPartService;
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
import java.util.Optional;

@Controller
@RequestMapping("/so")
public class SaleOrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SaleOrderController.class);

    @Autowired
    private ISaleOrderService saleOrderService;

    @Autowired
    private IShipmentTypeService shipmentTypeService;

    @Autowired
    private IWhUserTypeService whUserTypeService;

    @Autowired
    private IPartService partService;

    private void commonUI(Model model) {
        model.addAttribute("sos", shipmentTypeService.getShipmentIdAndCodeByEnable("YES"));
        model.addAttribute("wh", whUserTypeService.getWhUserIdAndCodeByType("Customer"));
    }

    /***
     * This method is used to show the register page to of the sale order
     * @param
     * @return SaleOrderrRegisterpage
     */
    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        commonUI(model);
        return "SaleOrderRegister";
    }

    /***
     * On click of CreateSaleOrder Button it will execute and save the sale Order
     * @param saleOrder
     * @param model
     * @return SaleOrderRegister Page
     */
    @PostMapping("/save")
    public String saveSaleOrder(@ModelAttribute SaleOrder saleOrder, Model model) {
        String message = "";
        try {
            LOGGER.info("Into sale order save method");
            Integer id = saleOrderService.saveSaleOrder(saleOrder);
            if (id != 0) {
                message = "Sale Order with id " + id + " created successfuly";
            }
            LOGGER.info("Exiting from sale order save method");
        } catch (Exception e) {
            e.printStackTrace();
            message = "Could not create sale Order , Please check application logs";
            LOGGER.error("Could not save sale order : {}", e.getMessage());
        }
        model.addAttribute("message", message);
        commonUI(model);
        return "SaleOrderRegister";
    }

    /***
     * It is the method executed when we click the All Button
     * @param model
     * @return
     */
    @GetMapping("/all")
    public String getAllSaleOrders(Model model) {
        String message = "";
        try {
            List<SaleOrder> response = saleOrderService.getAllSaleOrders();
            if (response.isEmpty()) {
                message = "No Sale Orders Found";
            } else {
                message = "Found " + response.size() + " Sale Orders";
            }
            model.addAttribute("list", response);
        } catch (SaleOrderNotFoundException se) {
            se.printStackTrace();
            LOGGER.error(se.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("Could not fetch sale orders  : {0} ", e.getMessage());
        }
        model.addAttribute("message", message);
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

    @GetMapping("/parts")
    public String showParts(@RequestParam Integer id, Model model) {
        try {
            LOGGER.info("Entered into Sale Order showParts method ");

            // Display Order Code and Status
            SaleOrder sOrder = saleOrderService.getOneSaleOrder(id);
            model.addAttribute("so", sOrder);

            model.addAttribute("part",partService.getPartIdAndCode());


            LOGGER.debug("Exit from sale Order Show Parts  method");
        } catch (SaleOrderNotFoundException snfe) {
            LOGGER.error("Could not find sale Order : {0}", snfe.getMessage());
            throw snfe;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "SaleOrderParts";
    }

    /***
     * Add Sale Order Details Using Form On Parts page.
     * @param saleDtl
     */

    @PostMapping("/addpart")
    public String addpart(
            @ModelAttribute SaleDtl saleDtl// Sale Order Id
    ) {

        Integer id = saleDtl.getSo().getId();  //  SaleId
        Integer partId = saleDtl.getPart().getId(); // PartId
        String status =saleOrderService.getCurrentStatusById(id) ; // Get Sale Order Status by soId

        if(SaleOrderStatus.OPEN.name().equals(status) || SaleOrderStatus.READY.name().equals(status)) {

           Optional<SaleDtl> saleDtlExist =  saleOrderService.getSaleOrderBySaleIdAndPartId(id, partId);
            if (saleDtlExist.isPresent()) {
                saleOrderService.updateSaleDetail(saleDtlExist.get().getId(), saleDtl.getQty());
                LOGGER.debug("SALE ORDER DETAIL UPDATED");
            } else {
                saleOrderService.saveSaleDetail(saleDtl);
                LOGGER.debug("SALE ORDER DETAIL ADDED");
            }
            if(SaleOrderStatus.OPEN.name().equals(status)) {
                saleOrderService.updateSaleOrderStatus(id,SaleOrderStatus.READY.name());

            }

        } else {
            LOGGER.error("Sale Order Status is not correct");
        }
        return "redirect:parts?id="+id;
    }

}
