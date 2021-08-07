package com.warehouse.controller;

import com.warehouse.model.ShipmentType;
import com.warehouse.service.IShipmentTypeService;
import com.warehouse.util.ShipmentTypeUtil;
import com.warehouse.view.ShipmentTypeExcelView;
import com.warehouse.view.ShipmentTypePdfView;
import org.hibernate.exception.ConstraintViolationException;
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
@RequestMapping("/st")
public class ShipmentTypeController {

    private final static Logger LOG = LoggerFactory.getLogger(ShipmentTypeController.class);

    @Autowired
    private IShipmentTypeService service;

    @Autowired
    private ShipmentTypeUtil util;

    @Autowired
    private ServletContext context;

    // 1. Show Register Page
    @GetMapping("/register")
    public String showRegisterPage() {
        return "ShipmentTypeRegister";
    }


    //2 Save Operation
    @PostMapping("/save")
    public String saveShipmentType(@ModelAttribute ShipmentType shipmentType, Model model) {

        try {
            Integer id = service.saveShipmentType(shipmentType);
            String message = "Shipment Type " + id + " saved successfully";
            model.addAttribute("message", message);
            return "ShipmentTypeRegister";
        } catch (Exception e) {
            LOG.error("Error ", e.getMessage());
            return null;
        }
    }

    // 3. Fetch Operation
    @GetMapping("/all")
    public String fetchShipmentTypes(Model model) {
        List<ShipmentType> list = null;
        try {
            list = service.getAllShipmentType();
            model.addAttribute("list", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ShipmentTypeData";
    }

    // 4. Edit Operation
    @GetMapping("/edit")
    public String editShipmentType(@RequestParam Integer id, Model model) {

        try {
            LOG.info("Entered into editShipmentType method");
            ShipmentType shipmentType = service.getOneShipmentType(id);
            model.addAttribute("shipmentTypeData", shipmentType);
            LOG.info("Exit from editShipmentType method");
        } catch (Exception e) {
            LOG.error("{}", e.getMessage());
        }
        return "ShipmentTypeEdit";
    }

    // 5. delete Operation
    @GetMapping("/delete")
    public String deleteShipmentType(@RequestParam Integer id, Model model) {
        String message = "";
        try {
            LOG.info("Entered into delete Shipment Type method");
            if (service.deleteShipType(id)) {
                message = "Shipment Type with " + id + " deleted successfully";
            }
            fetchAllShipmentTypes(model);
        } catch (Exception e) {
            LOG.error("{}", e.getMessage());
            message = e.getMessage();
            fetchAllShipmentTypes(model);
        }
        model.addAttribute("message", message);
        return "ShipmentTypeData";
    }

    private void fetchAllShipmentTypes(Model model) {
        List<ShipmentType> list = service.getAllShipmentType();
        LOG.info("FOUND DATA with size {}", list != null ? list.size() : "NO Data Found");
        model.addAttribute("list", list);
    }


    // 6. Update Operation
    @PostMapping("/update")
    public String updateShipmentType(@ModelAttribute ShipmentType shipmentType, Model model) {
        String message = "";
        try {
            LOG.info("Entered into update ShipmentType Method");
            Integer id = service.updateShipmentType(shipmentType);
            message = "ShipmentType with id " + id + " updated successfully";
            model.addAttribute("message", message);
            fetchAllShipmentTypes(model);

        } catch (ConstraintViolationException e) {
            LOG.error("Could not update due to {}", e.getMessage());
//            message = "Update Operation Failed check application logs for more details";
//            model.addAttribute("message", e.getMessage());
        }
        return "redirect:all";
    }

    @GetMapping("/validate")
    @ResponseBody
    public String validateShipmentCode(@RequestParam String code, @RequestParam Integer id) {
        String message = "";
        System.out.println("Code is  " + code);
        try {
            LOG.info("Entered into validate Shipment Code method");
            if (id == 0 && service.isShipmentCodeExists(code)) {
                message = "Shipment Code " + code + " already exists";
            }
            if (id != 0 && service.isShipmentCodeExists(code, id)) {
                message = "Shipment Code " + code + " already exists";
            }
            LOG.debug("Message  {} ", message);
            LOG.info("Exit from validate Shipment Code method");
        } catch (Exception e) {
            LOG.error("Could not validate : {0}", e.getMessage());
        }
        return message;
    }


    @GetMapping("/excel")
    public ModelAndView exportExcel() {
        ModelAndView m = null;
        try {
            m = new ModelAndView();
            m.setView(new ShipmentTypeExcelView());
            List<ShipmentType> list = service.getAllShipmentType();
            m.addObject("list", list);
        } catch (Exception e) {
            LOG.error("Could not Export because : {}", e.getMessage());
            e.printStackTrace();
        }
        return m;
    }

    @GetMapping("/charts")
    public String getShipmentModeChart() {
        try {
            LOG.info("Entered into Shipment Chart method");
            List<Object[]> shipmentTypeModeAndCount = service.getShipmentModeAndCount();
            String path = context.getRealPath("/");
            util.generatePieChart(path, shipmentTypeModeAndCount);
            util.generateBarChart(path, shipmentTypeModeAndCount);
            LOG.info("About to leave Shipment Chart method");
        } catch (Exception e) {
            LOG.error("Could not generate chart : {} ", e.getMessage());
            e.printStackTrace();
        }
        return "ShipmentTypeChart";
    }

    @GetMapping("/pdf")
    public ModelAndView showPdf() {
        ModelAndView m = new ModelAndView();
        m.setView(new ShipmentTypePdfView());
        m.addObject("list",service.getAllShipmentType());
        return m;

    }

}
