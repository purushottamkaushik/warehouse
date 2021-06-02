package com.warehouse.controller;

import com.warehouse.model.OrderMethod;
import com.warehouse.service.IOrderMethodService;
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

@Controller
@RequestMapping("/om")
public class OrderMethodController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderMethodController.class);

    @Autowired
    private ServletContext context;

    @Autowired
    private OrderMethodUtil util;

    @Autowired
    private IOrderMethodService service; // HAS-A Relation

    @GetMapping("/register")
    public String showRegisterPage() {
        LOGGER.info("Into Order Register Page");
        return "OrderMethodRegister";
    }

    @PostMapping("/save")
    public String saveOrderMethod(@ModelAttribute OrderMethod orderMethod, Model model) {
        try {
            LOGGER.info("Entered into save order method");
            Integer id = service.saveOrderMethod(orderMethod);
            model.addAttribute("message", "Order with id'" + id + " 'created Success");
            LOGGER.debug("Exit save order method");
        } catch (Exception e) {
            LOGGER.info("Could not save order method {} ", e.getMessage());
            model.addAttribute("message", "Check applivation logs");
        }
        return "OrderMethodRegister";
    }

    @GetMapping("/all")
    public String getAllOrder(Model model) {

        try {
            LOGGER.info("Entered into get All method");
            List<OrderMethod> list = service.getAllOrderMethod();
            model.addAttribute("list", list);
            model.addAttribute("message", list == null ? "No Data Found" : "Found data with size" + list.size());

            LOGGER.info("About to leave getAll method");
        } catch (Exception e) {
            LOGGER.info("Could not get Orders : {}", e.getMessage());
            model.addAttribute("message", "Could not fetch data please check  application logs");
        }
        return "OrderMethodData";
    }

    @GetMapping("/edit")
    public String editOrderMethod(@RequestParam Integer id, Model model) {
        try {
            LOGGER.info("Enter into edit method");
            OrderMethod om = service.getOneOrderMethod(id);
            model.addAttribute("orderMethodObject", om);
            LOGGER.info("Exit from edit method");
        } catch (Exception e) {
            LOGGER.error("Could not execute EDIT Method {}", e.getMessage());
        }
        return "OrderMethodEdit";
    }

    private void fetchCommonData(Model model) {
        List<OrderMethod> om = service.getAllOrderMethod();
        model.addAttribute("list", om);
    }

    @PostMapping("/update")
    public String updateOrderMethod(@ModelAttribute OrderMethod om, Model model) {
        try {
            LOGGER.info("Enter into update Order method");
            service.updateOrderMethod(om);
            fetchCommonData(model);
            model.addAttribute("message", "Order method with id '" + om.getId() + "' Updated Successfully");
            LOGGER.info("Exit from edit method");
        } catch (Exception e) {
            LOGGER.error("Could not execute EDIT Method {}", e.getMessage());

        }
        return "OrderMethodData";
    }

    @GetMapping("/delete")
    public String deleteOrderMethod(@RequestParam Integer id, Model model) {
        try {
            LOGGER.info("Enter into delete Order method");
            service.deleteOrderMethod(id);
            fetchCommonData(model);
            model.addAttribute("message", "Order method with id '" + id + "' Deleted Successfully");
            LOGGER.info("Exit from edit method");
        } catch (Exception e) {
            LOGGER.error("Could not execute EDIT Method {}", e.getMessage());
            model.addAttribute("message", "Order method with '" + id + "' not found");
        }
        return "OrderMethodData";
    }

    @GetMapping("/excel")
    public ModelAndView generateExcel() {
        ModelAndView m = null;
        try {
            m = new ModelAndView();
            m.setView(new OrderMethodExcelView());

            List<OrderMethod> list = service.getAllOrderMethod();
            m.addObject("list", list);
        } catch (Exception e) {
            LOGGER.error("Could not generate excel : {}", e.getMessage());

        }
        return m;
    }

    @GetMapping("/charts")
    public String generateCharts() {
        try {
            List<Object[]> list = service.getOrderModeCount();
            String path = context.getRealPath("/"); // root folder
            util.generatePieChart(list, path);
            util.generateBarChart(list, path);

        } catch (Exception e) {
            LOGGER.error("Could not generate chart {} : ", e.getMessage());

        }
        return "OrderMethodChart";
    }

    @GetMapping("/validate")
    @ResponseBody
    public String validateOrderMethodCode(@RequestParam Integer id, @RequestParam String code) {
        String message = "";
        try {
            if (id == 0 && service.isOrderCodeExists(code)) {
                message = "Order method with order code'" + code + "' already exists";
            }
            if (id != 0 && service.isOrderCodeExists(code, id)) {
                message = "Order method with order code'" + code + "' already exists";

            }
        } catch (Exception e) {
            LOGGER.error("Could not validate Order method code {}", e.getMessage());
        }
        return message;
    }

}
