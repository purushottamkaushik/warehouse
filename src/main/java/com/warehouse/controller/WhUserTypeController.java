package com.warehouse.controller;

import com.warehouse.model.WhUserType;
import com.warehouse.service.IWhUserTypeService;
import com.warehouse.util.EmailUtil;
import com.warehouse.util.WhUserTypeUtil;
import com.warehouse.view.WhUserTypeExcelView;
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
@RequestMapping("/wh")
public class WhUserTypeController {
    //
//
    @Autowired
    private IWhUserTypeService service;

    @Autowired
    private ServletContext context;

    @Autowired
    private WhUserTypeUtil util;

    @Autowired
    private EmailUtil emailUtil;

    private static final Logger LOGGER = LoggerFactory.getLogger(WhUserTypeController.class);

    @GetMapping("/register")
    public String showRegisterPage() {
        return "WhUserTypeRegister";
    }


    @PostMapping("/save")
    public String saveWhUserType(@ModelAttribute WhUserType whUserType, Model model) {
        try {
            LOGGER.info("Entered into WhType Save method");
            Integer id = service.addWhUserType(whUserType);

            // After id created successfully send email for sending notification
            if( id>0) {
                new Thread(()->{
                    emailUtil.sendEmail(whUserType.getUserEmail(),"User Registereed",
                            "user Registered with code : " + whUserType.getUserCode());
                }).start();
            }
            String message = "WhUser with id '" + id + "' saved Successfully";
            model.addAttribute("message", message);
            LOGGER.debug("Exiting from WhType Save method");
        } catch (Exception e) {
            LOGGER.error("Could not execute statement : {}", e.getMessage());
        }
        return "WhUserTypeRegister";
    }

    //
    @GetMapping("/all")
    public String getAllWhUserType(Model model) {
        try {
            LOGGER.info("Entered into Wh User GetAll method");
            fetchCommonData(model);
            LOGGER.debug("Exiting from Wh User GetAll method");
        } catch (Exception e) {
            LOGGER.error("Could not fetch data : {}", e.getMessage());
        }
        return "WhUserTypeData";
    }

    private void fetchCommonData(Model model) {
        List<WhUserType> list = service.getAllWhUsers();
        model.addAttribute("whUserTypeObjects", list);
        model.addAttribute("message", "Found data with size " + list == null ? "0" : list.size());
    }


    @GetMapping("/delete")
    private String deleteWhUserType(@RequestParam Integer id, Model model) {
        try {
            LOGGER.info("Entered into WhUserType Module DELETE Method");
            service.deleteWhUserType(id);
            fetchCommonData(model);
            LOGGER.debug("Exiting from  WhUserType Module DELETE Method");

        } catch (Exception e) {
            LOGGER.error("Could not delete : {} ", e.getMessage());
        }
        return "WhUserTypeData";
    }

    @GetMapping("/edit")
    public String editWhUserType(@RequestParam Integer id, Model model) {
        try {
            LOGGER.info("Entered into WHUserType EDIT Method");
            WhUserType whUserType = service.getOneWhUserType(id);
            model.addAttribute("whUserTypeObject", whUserType);
            LOGGER.debug("Exit from WHUserType EDIT Method");
        } catch (Exception e) {
            LOGGER.error("Could not execute : {} ", e.getMessage());
        }
        return "WhUserTypeEdit";
    }

    //
    @PostMapping("/update")
    public String updateWhUserType(@ModelAttribute WhUserType whUserType, Model model) {
        try {
            LOGGER.info("Entered into WHUserType UPDATE Method");
            service.updateWhUserType(whUserType);
            fetchCommonData(model);
            LOGGER.debug("Exiting from WHUserType UPDATE Method");
        } catch (Exception e) {
            LOGGER.error("Could not update WhUserType {}", e.getMessage());
        }
        return "WhUserTypeData";
    }

    @GetMapping("/validatecode")
    @ResponseBody
    public String validateCode(@RequestParam String code, @RequestParam Integer id) {
        String message = "";
        try {
            LOGGER.info("Entered into WhUserType validate code method ");
            if (id == 0 && service.validateWhUserTypeCode(code)) {
                message = "WhType Code '" + code + "' Already Exists";
            }
            if (id != 0 && service.validateWhUserTypeCode(code, id)) {
                message = "WhType Code '" + code + "' Already Exists";
            }
        } catch (Exception e) {
            LOGGER.error("Could not validate code : {} ", e.getMessage());
        }
        LOGGER.debug("Message From Validate Code : {} ", message);
        LOGGER.info("Exiting from  WhUserType validate code method ");
        return message;
    }

    @GetMapping("/validateemail")
    @ResponseBody
    public String validateEmail(@RequestParam String email, @RequestParam Integer id) {
        String message = "";
        try {
            LOGGER.info("Entered into  WhUserType validate Email method ");

            if (id == 0 && service.validateWhUserTypeEmail(email)) {
                message = "WhTypeUser Email '" + email + "' Already Exists";
            }
            if (id != 0 && service.validateWhUserTypeEmail(email, id)) {
                message = "WhTypeUser Email '" + email + "' Already Exists";
            }
            LOGGER.info("Exiting from  WhUserType validate Email method ");
        } catch (Exception e) {
            LOGGER.error("Could validate email :  {} ", e.getMessage());
        }
        return message;
    }

    @GetMapping("/validateidnum")
    @ResponseBody
    public String validateIdNumber(@RequestParam String idNumber, @RequestParam Integer id) {
        String message = "";
        try {
            LOGGER.info("Entered into  WhUserType validate Id Number method ");

            if (id == 0 && service.validateWhUserTypeIdNumber(idNumber)) {
                message = "WhTypeUser ID number '" + idNumber + "' Already Exists";
            }
            if (id != 0 && service.validateWhUserTypeIdNumber(idNumber, id)) {
                message = "WhTypeUser Email '" + idNumber + "' Already Exists";
            }
            LOGGER.info("Exiting from  WhUserType validate ID number method ");
        } catch (Exception e) {
            LOGGER.error("Could validate email :  {} ", e.getMessage());
        }
        return message;
    }
    //

    @GetMapping("/excel")
    public ModelAndView generateWhUserTypeExcel() {
        ModelAndView m = null;
        try {
            LOGGER.info("Entered into WhUserType Excel generation method");
            m = new ModelAndView();
            m.setView(new WhUserTypeExcelView());
            m.addObject("list", service.getAllWhUsers());
            LOGGER.info("Exiting from  WhUserType Excel generation method");
        } catch (Exception e) {
            LOGGER.error("Could not generate Excel : {}", e.getMessage());
        }
        return m;
    }

    @GetMapping("/charts")
    public String generateWhUserTypeChart() {
        try {
            LOGGER.info("Entered into WhUserType Chart method");
            List<Object[]> data = service.getUserTypeCount();
            String path = context.getRealPath("/");
            util.generateBarChart(path, data);
            util.generateBarChart(path, data);
            LOGGER.info("Exiting from WhUserType Chart method");
        } catch (Exception e) {
            LOGGER.error("Could not generate Chart : {} ", e.getMessage());
        }
        return "WhUserTypeCharts";
    }
}

