package com.warehouse.controller;

import com.warehouse.consts.SaleOrderStatus;
import com.warehouse.consts.ShippingStatus;
import com.warehouse.model.SaleDtl;
import com.warehouse.model.Shipping;
import com.warehouse.model.ShippingDtl;
import com.warehouse.service.ISaleOrderService;
import com.warehouse.service.IShippingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
            createShippingDetails(shipping);
            Integer id = shippingService.saveShipping(shipping);
            String message = "Shipping with id " + id + " created Successfully" ;
            model.addAttribute("message", message);
            saleOrderService.updateSaleOrderStatus(shipping.getSo().getId(), SaleOrderStatus.SHIPPED.name());
            LOGGER.debug("Exiting from Shipping Save method ");
        } catch (Exception e) {
            LOGGER.error("Could not execute Shiipping method  : {} " + e.getMessage());
        }
        commonUi(model);
        return "ShippingRegisterPage";
    }

    private void createShippingDetails(Shipping shipping) {
        Integer id = shipping.getSo().getId();
        List<SaleDtl> saleDtls = saleOrderService.getSaleOrderDetailsBySoId(id);
        Set<ShippingDtl> shippingDtlList = new HashSet<>();

        for (SaleDtl dtl : saleDtls) {
            ShippingDtl shippingDtl = new ShippingDtl();
            shippingDtl.setBaseCost(dtl.getPart().getPartBaseCost()); // Setting base Cost
            shippingDtl.setItemCode(dtl.getSo().getOrderCode());
            shippingDtl.setQuantity(dtl.getQty());
            shippingDtlList.add(shippingDtl);
        }
        LOGGER.info("Shipping Details Saved having length :{} ", shippingDtlList.size());
        shipping.setShippingDtls(shippingDtlList);
    }

    @GetMapping("/all")
    public String getAllShippings(Model model) {
        try {
            LOGGER.info("Entered into Shipping Module GET All shippings method");
            List<Shipping> shippingList = shippingService.getAllShippings();
            model.addAttribute("list", shippingList);
            LOGGER.debug("Exiting from getAllShipping method");
        } catch (Exception e) {
            LOGGER.error("Could not fetch shipping : {} ", e.getMessage());
        }
        return "ShippingDataPage";
    }

    @GetMapping("/parts")
    public String getShippingPartsPage(@RequestParam Integer id, Model model) {
        try {
            LOGGER.info("Entered into Shipping Module Parts method");
            Shipping shipping = shippingService.getOneShipping(id);
            Set<ShippingDtl> shippingDtls = shipping.getShippingDtls();
            model.addAttribute("sp", shipping);
            model.addAttribute("shippingDtls", shippingDtls);
            LOGGER.debug("Exiting from Shipping Module Parts Page ");

        } catch (Exception e) {
            LOGGER.error("Could not perform Operation : {} " , e.getMessage());
        }
        return "ShippingDataView";
    }

    @GetMapping("/accept" )
    public String acceptStatus(@RequestParam Integer shipDtlId , @RequestParam Integer shipId) {
        try {
            LOGGER.info("Enter into Shipping MODULE ACCEPT METHOD");
            shippingService.updateShippingStatus(shipDtlId, ShippingStatus.ACCEPTED.name());
            LOGGER.info("Exiting from Shipping MODULE ACCEPT METHOD");
        } catch (Exception e) {
            LOGGER.error("Could not perform accept Operation : {} ",e.getMessage());
        }
        return "redirect:parts?id="+shipId;
    }

    @GetMapping("/reject" )
    public String rejectStatus(@RequestParam Integer shipDtlId , @RequestParam Integer shipId) {
        try {
            LOGGER.info("Enter into Shipping MODULE REJECT METHOD");
            shippingService.updateShippingStatus(shipDtlId, ShippingStatus.REJECTED.name());
            LOGGER.debug("Exiting from SHIPPING MODULE REJECT METHOD ");
        } catch (Exception e) {
            LOGGER.error("Could not perform Reject Operation : {} ",e.getMessage());
        }
        return "redirect:parts?id="+shipId;
    }

}
