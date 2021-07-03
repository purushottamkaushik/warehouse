package com.warehouse.controller;

import com.warehouse.consts.GrnStatus;
import com.warehouse.consts.PurchaseOrderStatus;
import com.warehouse.model.Grn;
import com.warehouse.model.GrnDetail;
import com.warehouse.model.PurchaseDtl;
import com.warehouse.model.PurchaseOrder;
import com.warehouse.service.IGrnService;
import com.warehouse.service.IPurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/grn")
public class GrnController {

    @Autowired
    private IPurchaseOrderService purchaseOrderService;

    @Autowired
    private IGrnService grnService;

    /***
     *
     * @param model
     * @return GrnRegisterPage
     */
    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("list",purchaseOrderService.getPoByStatus(PurchaseOrderStatus.INVOICED.name()));
        return "GrnRegisterPage";
    }

    @PostMapping("/save")
    public String saveGrn(@ModelAttribute Grn grn,Model model) {
        createGrnDetails(grn);
        Integer id = grnService.addGrn(grn);
        // Updating PO Status to recieved
        String message = "";
        if(id !=null) {
            purchaseOrderService.updateCurrentStatusByPoId(grn.getPo().getId(),PurchaseOrderStatus.RECEIVED.name());
            message = "GRn with id ' " + id + " ' Saved Success";
        }
        model.addAttribute("message",message);

        return "GrnRegisterPage";

    }

    /***
     *
     * @param grn
     */
    private void createGrnDetails(Grn grn) {
        // Initializing the grnDetails List
        Set<GrnDetail> grnDetails = new HashSet<>();
        Integer poId = grn.getPo().getId();
        List<PurchaseDtl> purchaseDetails =purchaseOrderService.getPurchaseDetailsByPoId(poId) ;

        // Filling the GrnDetail Object
        for (PurchaseDtl dtl : purchaseDetails) {
            GrnDetail detail = new GrnDetail();
            detail.setBaseCost(dtl.getPart().getPartBaseCost());
            detail.setPartCode(dtl.getPart().getPartCode());
            detail.setQty(dtl.getQty());
            grnDetails.add(detail);
        }
        grn.setGrnDetails(grnDetails);
    }

    @GetMapping("/all")
    public String getAllGrns(Model model) {
        try{
            List<Grn> grnList = grnService.getAllGrns();
            model.addAttribute("list",grnList);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return "GrnDataPage";
    }

    @GetMapping("/parts")
    public String grnParts(@RequestParam Integer id , Model model){
        try{
            Grn grn = grnService.getOneGrn(id);
            Set<GrnDetail> grnDetails = grn.getGrnDetails();
            model.addAttribute("grn",grn);
            model.addAttribute("grnDetails",grnDetails);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return "GrnPartData";
    }

    @GetMapping("/accept")
    public String acceptStatus(@RequestParam Integer grnDetailId, @RequestParam Integer grnId) {

        grnService.updateGrnDetailsById(grnDetailId, GrnStatus.ACCEPTED.name());
        return "redirect:parts?id="+grnId;
    }


    @GetMapping("/reject")
    public String rejectStatus(@RequestParam Integer grnDetailId, @RequestParam Integer grnId) {

        grnService.updateGrnDetailsById(grnDetailId, GrnStatus.REJECTED.name());
        return "redirect:parts?id="+grnId;
    }





}
