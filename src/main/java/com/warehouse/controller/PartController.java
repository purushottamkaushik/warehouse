package com.warehouse.controller;

import com.warehouse.model.OrderMethod;
import com.warehouse.model.Part;
import com.warehouse.service.IOrderMethodService;
import com.warehouse.service.IPartService;
import com.warehouse.service.IUomService;
import com.warehouse.util.OrderMethodUtil;
import com.warehouse.view.OrderMethodExcelView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/part")
public class PartController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PartController.class);

    @Autowired
    private ServletContext context;

//    @Autowired
//    private OrderMethodUtil util;

    @Autowired
    private IPartService service; // HAS-A Relation

    @Autowired
    private IUomService uomService;

    @Autowired
    private IOrderMethodService omService;

    private void getAllUomIdAndModel(Model model) {
        model.addAttribute("uoms",uomService.getUomIdAndModel());
        model.addAttribute("oms",omService.getAllOrderCodes());
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        LOGGER.info("Into Part register page");
        getAllUomIdAndModel(model);
        return "PartRegister";
    }

    @PostMapping("/save")
    public String savePart(@ModelAttribute Part part, Model model) {
        try {
            LOGGER.info("Entered into save Part method");
            Integer id = service.savePart(part);
            model.addAttribute("message", "Part with id'" + id + " 'created Success");
            LOGGER.debug("Exit part SAVE method");
        } catch (Exception e) {
            LOGGER.info("Could not SAVE part : {} ", e.getMessage());
            model.addAttribute("message", e.getMessage());
        }
        return "PartRegister";
    }

    @GetMapping("/all")
    public String getAllParts(Model model) {

        try {
            LOGGER.info("Entered into getALl PARTS  method");
            List<Part> list = service.getAllParts();
            model.addAttribute("list", list);
            model.addAttribute("message", list == null ? "No Data Found" : "Found data with size" + list.size());
            LOGGER.info("About to leave PART  getAll method");
        } catch (Exception e) {
            LOGGER.info("Could not get Parts  : {}", e.getMessage());
            model.addAttribute("message", "Could not fetch data please check  application logs");
        }
        return "PartData";
    }
//
//    @GetMapping("/edit")
//    public String editOrderMethod(@RequestParam Integer id, Model model) {
//        try {
//            LOGGER.info("Enter into edit method");
//            OrderMethod om = service.getOneOrderMethod(id);
//            model.addAttribute("orderMethodObject", om);
//            LOGGER.info("Exit from edit method");
//        } catch (Exception e) {
//            LOGGER.error("Could not execute EDIT Method {}", e.getMessage());
//        }
//        return "OrderMethodEdit";
//    }
//
//    private void fetchCommonData(Model model) {
//        List<OrderMethod> om = service.getAllOrderMethod();
//        model.addAttribute("list", om);
//    }
//
//    @PostMapping("/update")
//    public String updateOrderMethod(@ModelAttribute OrderMethod om, Model model) {
//        try {
//            LOGGER.info("Enter into update Order method");
//            service.updateOrderMethod(om);
//            fetchCommonData(model);
//            model.addAttribute("message", "Order method with id '" + om.getId() + "' Updated Successfully");
//            LOGGER.info("Exit from edit method");
//        } catch (Exception e) {
//            LOGGER.error("Could not execute EDIT Method {}", e.getMessage());
//
//        }
//        return "OrderMethodData";
//    }
//
//    @GetMapping("/delete")
//    public String deleteOrderMethod(@RequestParam Integer id, Model model) {
//        try {
//            LOGGER.info("Enter into delete Order method");
//            service.deleteOrderMethod(id);
//            fetchCommonData(model);
//            model.addAttribute("message", "Order method with id '" + id + "' Deleted Successfully");
//            LOGGER.info("Exit from edit method");
//        } catch (Exception e) {
//            LOGGER.error("Could not execute EDIT Method {}", e.getMessage());
//            model.addAttribute("message", "Order method with '" + id + "' not found");
//        }
//        return "OrderMethodData";
//    }
//
//    @GetMapping("/excel")
//    public ModelAndView generateExcel() {
//        ModelAndView m = null;
//        try {
//            m = new ModelAndView();
//            m.setView(new OrderMethodExcelView());
//
//            List<OrderMethod> list = service.getAllOrderMethod();
//            m.addObject("list", list);
//        } catch (Exception e) {
//            LOGGER.error("Could not generate excel : {}", e.getMessage());
//
//        }
//        return m;
//    }
//
//    @GetMapping("/chart")
//    public String generateCharts() {
//        try {
//            List<Object[]> list = service.getOrderModeCount();
//            String path = context.getRealPath("/"); // root folder
//            util.generatePieChart(list, path);
//            util.generateBarChart(list, path);
//
//        } catch (Exception e) {
//            LOGGER.error("Could not generate chart {} : ", e.getMessage());
//
//        }
//        return "OrderMethodChart";
//    }
//
//    @GetMapping("/validate")
//    @ResponseBody
//    public String validateOrderMethodCode(@RequestParam Integer id, @RequestParam String code) {
//        String message = "";
//        try {
//            if (id == 0 && service.isOrderCodeExists(code)) {
//                message = "Order method with order code'" + code + "' already exists";
//            }
//            if (id != 0 && service.isOrderCodeExists(code, id)) {
//                message = "Order method with order code'" + code + "' already exists";
//
//            }
//        } catch (Exception e) {
//            LOGGER.error("Could not validate Order method code {}", e.getMessage());
//        }
//        return message;
//    }

}
