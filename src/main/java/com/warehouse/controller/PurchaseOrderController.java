package com.warehouse.controller;

import com.warehouse.consts.PurchaseOrderStatus;
import com.warehouse.customexception.UomNotFoundException;
import com.warehouse.model.PurchaseDtl;
import com.warehouse.model.PurchaseOrder;
import com.warehouse.model.Uom;
import com.warehouse.repo.PartRepository;
import com.warehouse.repo.PurchaseDetailRepository;
import com.warehouse.service.*;
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
import java.util.Optional;

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

    @Autowired
    private IShipmentTypeService shipmentTypeService;

    @Autowired
    private IWhUserTypeService whUserTypeService;

    @Autowired
    private IPartService partService;



    private void commonUi(Model model) {
        model.addAttribute("sts",shipmentTypeService.getShipmentIdAndCodeByEnable("YES"));
        model.addAttribute("vendors",whUserTypeService.getWhUserIdAndCodeByType("Vendor"));
    }


    // 1. Show register page
    @GetMapping("/register")
    public String registerUom(Model model) {
        commonUi(model);
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
        commonUi(model);
        return "PurchaseOrderRegister";
    }

    // 3. Fetch all Purchase Orders
    @GetMapping("/all")
    public String getAllPurchaseOrders(Model model) {

        try {
            LOG.info("Entered into getAllPurchaseOrders method");

            List<PurchaseOrder> purchaseOrders = poService.getPurchaseOrders();
            model.addAttribute("list", purchaseOrders);
            commonUi(model);
            LOG.info("Exiting from getAllPurchaseOrders method");
        } catch (Exception e) {
            LOG.error("Could not fetch Purchase Orders: {}", e.getMessage());
        }
        return "PurchaseOrderData";

    }

    // =======================# Screen 2========================================

    private void commonUiParts(Model model) {
        model.addAttribute("parts",partService.getPartIdAndCode()); // Dynamic DropDown
    }

    @GetMapping("/parts")
    public String showPartsPage(@RequestParam("id") Integer id , Model model) {
        try{
            LOG.info("Entered into parts PO parts method");
            PurchaseOrder po = poService.getOnePurchaseOrder(id);
            model.addAttribute("po",po);

            // All Purchase Details for a specific purchase Id poId
            List<PurchaseDtl> poDtls = poService.getPurchaseDetailsByPoId(id);
            model.addAttribute("list",poDtls);
            LOG.info("Exiting from parts PO parts method");
        } catch (Exception e) {
            LOG.error("Could not show parts : {}",e.getMessage());
            e.printStackTrace();
        }

        commonUiParts(model); //  For Dynamic DropDown for parts
        return "PurchaseOrderParts";
    }


    /**
     *  This method will invoke when we click on add button
     *
     * */
    @PostMapping("/addpart")
    public String addPart(PurchaseDtl dtl ) {
        try {
            LOG.info("Adding PO part");
            // Checking part exists or not if exists then update the quantity
            Integer poId = dtl.getPo().getId();
            Integer partId = dtl.getPart().getId();

            Optional<PurchaseDtl> purchaseDtlOptional = poService.getPurchaseDetailsByPoIdAndPartId(poId,partId);

            if (purchaseDtlOptional.isPresent()) {  // If the purchase detail related with PO and part exists then update
                poService.updatePurchaseDetailQtyById(dtl.getQty(),purchaseDtlOptional.get().getId());
            } else {
                poService.savePurchaseDetail(dtl);
            }

            if (PurchaseOrderStatus.OPEN.name().equals(poService.getCurrentStatusByPoId(poId))) {
                poService.updateCurrentStatusByPoId(poId,PurchaseOrderStatus.PICKING.name());
            }
            LOG.info("Part Added Success");
            // Returning to the same page
            return "redirect:parts?id=" + dtl.getPo().getId();
        } catch (Exception e) {
            LOG.error("Could not Add Part : {}" , e.getMessage());
        }
        return  null;
    }

    @GetMapping("/deletepdtl")
    public String deletePurchaseDetail(@RequestParam Integer poId , @RequestParam Integer dtlId) {
        poService.deletePurchaseDetailById(dtlId);


        // Should check if there is no item in the Purchase Order
        if(poService.getPurchaseDetailCountByPoId(poId) == 0) {
            poService.updateCurrentStatusByPoId(poId,PurchaseOrderStatus.OPEN.name());
        }
        return "redirect:parts?id="+poId;
    }


}
