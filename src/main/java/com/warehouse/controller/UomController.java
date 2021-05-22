package com.warehouse.controller;

import com.sun.jdi.event.ExceptionEvent;
import com.warehouse.customexception.UomNotFoundException;
import com.warehouse.model.ShipmentType;
import com.warehouse.model.Uom;
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
@RequestMapping("/uom")
public class UomController {

    private final static Logger LOG = LoggerFactory.getLogger(UomController.class);

    @Autowired
    private IUomService uomService;

    @Autowired
    private UomUtil util;

    @Autowired
    private ServletContext context;

    // 1. Show register page
    @GetMapping("/register")
    public String registerUom() {
        return "UomRegister";
    }

    //2. Save Operation
    @PostMapping("/save")
    public String saveUom(@ModelAttribute Uom uom , Model model) {
        String message = "";
        try{
            LOG.info("Entered into UOM Save method");
            String id = uomService.saveUom(uom);
            model.addAttribute("message","Uom having id " + id + " created");
            LOG.info("Exiting from UOM Save method");
        } catch (Exception e) {
            LOG.error("Could not save : {}",e.getMessage());
            message = e.getMessage();
        }
        return "UomRegister";
    }

    // 3. Fetch all Uoms
    @GetMapping("/all")
    public String getAllUoms(Model model) {

        try {
            LOG.info("Entered into getAllUoms method");

            List<Uom> uoms = uomService.getAllUoms();
            model.addAttribute("list",uoms);
            LOG.info("Exiting from getAllUoms method");
        } catch (Exception e) {
            LOG.error("Could not fetch Uoms {}",e.getMessage());
        }
        return "UomData";

    }

    private void fetchAllCommonUom(Model model) {
        List<Uom> list = uomService.getAllUoms();
        model.addAttribute("list",list);
        LOG.info("Fetched Uom Data with size {}" ,list==null?"0":list.size());

    }

    // 4. Delete Uom
    @GetMapping("/delete")
    public String deleteUom(@RequestParam Integer id,Model model) {
        String message ="";
        try {
            LOG.info("Entered into delete Uom Method");
            uomService.deleteUom(id);
            message = "Uom with id " + id + " deleted successfully";
            model.addAttribute("message",message);
            fetchAllCommonUom(model);
            LOG.info("Exiting delete Uom Method");
        } catch (Exception e) {
            LOG.error("Could not delete : {}" , e.getMessage());
            model.addAttribute("message",e.getMessage());
        }
        return "UomData";
    }

    // 5. Edit Operation
    @GetMapping("/edit")
    public String editUom(@RequestParam Integer id,Model model) {
        try {
            LOG.info("Entered into edit Uom method");
            Uom uom = uomService.getOneUom(id);
            model.addAttribute("uomObject",uom);
            LOG.info("Exit from edit Uom method");
        }catch (UomNotFoundException ex) {
            LOG.error("Could not edit : {}" ,ex.getMessage());
            ex.printStackTrace();
        }
        return "UomEdit";
    }

    @GetMapping("/validate")
    @ResponseBody
    public String validateUom(@RequestParam String uomModel, @RequestParam Integer uomId,  Model model) {
        String message ="";
        try {
            LOG.info("Entered into validate Uom Method ");
            if(uomId == 0 && uomService.isUomModelExist(uomModel)) {
                message = "Uom model " + uomModel + " already exists";
            }
            if (uomId !=0 && uomService.isUomModelExistForEdit(uomModel,uomId)) {
                message = "Uom model " + uomModel + " already exists";
            }
            LOG.debug("Message = {} ",message);
            LOG.info("Exit from validate Uom Method ");
        }catch (Exception ex) {
            LOG.error("Could not Update : {}" ,ex.getMessage());
            ex.printStackTrace();
        }
        return message;
    }

    @PostMapping("/update")
    public String updateUom(@ModelAttribute Uom uom ,Model model) {
        try {
            LOG.info("Entered into Update Uom method");
            uomService.updateUom(uom);
            String message = "Uom with id " + uom.getId() + " updated Successfully";
            model.addAttribute("message",message);
            LOG.info("Exiting from Update Uom method");
        } catch (UomNotFoundException e ) {
            LOG.error("Could not update : {} " ,e.getMessage());
            model.addAttribute("message",e.getMessage());
        }
        fetchAllCommonUom(model);
        return "redirect:all";
    }

    // For excel functionality
    @GetMapping("/excel")
    public ModelAndView exportExcel() {
        ModelAndView m =null;
        try {
            LOG.info("Entered into export excel method ");
            m = new ModelAndView();
            m.setView(new UomExcelExport());
            List<Uom> list = uomService.getAllUoms();
            m.addObject("list",list);
            LOG.info("Exiting from export excel method ");
        } catch (Exception e) {
            LOG.error("Could not export excel :  {}" ,e.getMessage());
        }
        return m;
    }

    @GetMapping("/chart")
    public String getUomTypeChart(){
        try{
            LOG.info("Entered into UomTypeChart method");
            List<Object[]> uomTypeCount = uomService.getUomTypeCount();
            String path = context.getRealPath("/");
            util.generatePieChart(path,uomTypeCount);
            util.generateBarChart(path,uomTypeCount);
            LOG.info("Entered into UomTypeChart method");
        } catch (Exception e) {
            LOG.error("Could not generate chart : {} " , e.getMessage());
        }
        return "UomChart";
    }



}
