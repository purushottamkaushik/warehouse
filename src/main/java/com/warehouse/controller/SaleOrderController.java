package com.warehouse.controller;

import com.warehouse.consts.SaleOrderStatus;
import com.warehouse.customexception.SaleOrderNotFoundException;
import com.warehouse.model.SaleDtl;
import com.warehouse.model.SaleOrder;
import com.warehouse.service.IPartService;
import com.warehouse.service.ISaleOrderService;
import com.warehouse.service.IShipmentTypeService;
import com.warehouse.service.IWhUserTypeService;
import org.dom4j.rule.Mode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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

            model.addAttribute("part", partService.getPartIdAndCode());
            List<SaleDtl> saleDtls = saleOrderService.getSaleOrderDetailsBySoId(id);
            model.addAttribute("sos", saleDtls);
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
        try {

            String status = saleOrderService.getCurrentStatusById(id); // Get Sale Order Status by soId

            if (SaleOrderStatus.OPEN.name().equals(status) || SaleOrderStatus.READY.name().equals(status)) {

                Optional<SaleDtl> saleDtlExist = saleOrderService.getSaleOrderBySaleIdAndPartId(id, partId);
                if (saleDtlExist.isPresent()) {
                    saleOrderService.updateSaleDetail(saleDtlExist.get().getId(), saleDtl.getQty());
                    LOGGER.debug("SALE ORDER DETAIL UPDATED");
                } else {
                    saleOrderService.saveSaleDetail(saleDtl);
                    LOGGER.debug("SALE ORDER DETAIL ADDED");
                }
                if (SaleOrderStatus.OPEN.name().equals(status)) {
                    saleOrderService.updateSaleOrderStatus(id, SaleOrderStatus.READY.name());

                }

            } else {
                LOGGER.error("Sale Order Status is not correct");
            }
        } catch (Exception e) {
            LOGGER.error(" Could not add part  : {} ", e.getMessage());
        }
        return "redirect:parts?id=" + id;
    }

    @GetMapping("/deletesshipDtl")
    public String removeSaleDetail(@RequestParam Integer dtlId, @RequestParam Integer soId) {
        try {
            String status = saleOrderService.getCurrentStatusById(soId);
            if (status.equals(SaleOrderStatus.READY.name())) {  // check if the current status is READY ==> Having atleast one product
                saleOrderService.deleteSaleDtlById(dtlId);
                if (saleOrderService.isSaleDetailsExistBySoId(soId).intValue() == 0) {
                    saleOrderService.updateSaleOrderStatus(soId, SaleOrderStatus.OPEN.name());
                }
            }
        } catch (Exception e) {
            LOGGER.error("Could Not delete Shipment : {} ", e.getMessage());
        }
        return "redirect:parts?id=" + soId;
    }

    @GetMapping("/increaseqty")
    public String increaseQty(@RequestParam Integer soId,  // Required for
                              @RequestParam Integer dtlId) {
        try {
            LOGGER.info("Entered into Increase Quantity method");
            saleOrderService.updateSaleQuantity(dtlId, 1);
            LOGGER.debug("Exit from increase Quantity method");
        } catch (Exception e) {
            LOGGER.error("Could not Increase Qty : {} ", e.getMessage());
        }
        return "redirect:parts?id=" + soId;
    }

    @GetMapping("/decreaseqty")
    public String decreaseQty(@RequestParam Integer soId,  // Required for
                              @RequestParam Integer dtlId) {
        try {
            LOGGER.info("Entered into Decrease Quantity method");
            saleOrderService.updateSaleQuantity(dtlId, -1);
            LOGGER.debug("Exit from Decrease  Quantity method");
        } catch (Exception e) {
            LOGGER.error("Could not Increase Qty : {} ", e.getMessage());
        }
        return "redirect:parts?id=" + soId;
    }

    @GetMapping("/confirm")
    public String confirmShipping(@RequestParam Integer soId) {
        try {
            LOGGER.info("Entered into Sale Order Confirm method");
            saleOrderService.updateSaleOrderStatus(soId, SaleOrderStatus.CONFIRM.name());
            LOGGER.debug("Exit from sale Order confirm method ");
        } catch (Exception e) {
            LOGGER.error("Could not confirm : {} ", e.getMessage());
        }
        return "redirect:parts?id=" + soId;
    }

    @GetMapping("/generate")
    public String generateInvoice(@RequestParam Integer soId) {
        String status = saleOrderService.getCurrentStatusById(soId);
        try {
            LOGGER.info("Entered Into Sale Order Generate Invoice Method ");
            if (status.equals(SaleOrderStatus.CONFIRM.name())) {
                saleOrderService.updateSaleOrderStatus(soId, SaleOrderStatus.INVOICED.name());
            }
            LOGGER.debug("Exiting from Sale Order Generate Invoice method ");
        } catch (Exception e) {
            LOGGER.error("Could not generate invoice : {} " , e.getMessage());
        }
        return "redirect:all";
    }


    @GetMapping("/invoice")
    public ModelAndView printInvoice(@RequestParam Integer soId) {
        ModelAndView m = new ModelAndView();

        try{
            LOGGER.info("Entered Into Sale Order Print Invoice Method ");
            m.setView(new CustomerInvoicePdfView());
            m.addObject("so" , saleOrderService.getOneSaleOrder(soId));
            m.addObject("list",saleOrderService.getSaleOrderDetailsBySoId(soId));

            LOGGER.debug("Exiting from Sale Order Customer Invoice method ");
        } catch (Exception e) {
            LOGGER.error("Could not print invoice : {} " , e.getMessage());
        }
        return m;
    }

    @GetMapping("/cancel")
    public String cancelOrder(@RequestParam Integer soId) {
        String status = saleOrderService.getCurrentStatusById(soId);
        try {
            if (status.equals(SaleOrderStatus.OPEN.name()) || status.equals(SaleOrderStatus.READY.name()) || status.equals(SaleOrderStatus.CONFIRM.name()) || status.equals("INVOICED")) {
                saleOrderService.updateSaleOrderStatus(soId,SaleOrderStatus.CANCELLED.name());
            }
        }catch (Exception e) {
            LOGGER.error("Could Not Cancel Shipping : {} " , e.getMessage());
        }
        return "redirect:all";
    }


}
