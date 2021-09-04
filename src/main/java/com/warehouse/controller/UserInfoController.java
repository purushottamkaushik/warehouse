package com.warehouse.controller;

import com.warehouse.model.UserInfo;
import com.warehouse.service.IRoleService;
import com.warehouse.service.IUserInfoService;
import com.warehouse.util.EmailUtil;
import com.warehouse.util.MyAppUtil;
import com.warehouse.util.UserInfoUtils;
import org.dom4j.rule.Mode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserInfoController {

    // TODO No exception Handling
    @Autowired
    private IRoleService roleService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private EmailUtil mail;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserInfoController.class);


    //1 . Display Register Page
    @GetMapping("/register")
    public String showUserRegisterPage(Model model) {
        LOGGER.info("Into User register page method ");
        commonUI(model);
        LOGGER.debug("Exiting from UserINfo Register method ");
        return "UserInfoRegister";
    }

    // Method for generating the common UI
    private void commonUI(Model model) {
        Map<Integer,String> rolesMap = roleService.getRolesMap();
        model.addAttribute("roles",rolesMap);
    }


    @PostMapping("/create")
    public String saveUser(@ModelAttribute UserInfo userInfo, Model model ) {
        // Generate Password
        String password =MyAppUtil.genPwd();
        System.out.println(password);
        userInfo.setPassword(password);
        userInfoService.saveUser(userInfo);
        String text = "Your account has been created Successfully \n Email: " + userInfo.getEmail() + " password : " + password ;
        mail.sendEmail(userInfo.getEmail(), "Account Created Successfully" ,text);
        model.addAttribute("message" ,"User with email "  + userInfo.getEmail()+ " created successfully" );
        return "UserInfoRegister";
    }
// =======================================================================================

    @GetMapping("/login")
    public String showLoginPage(){
        return "UserLoginPage";
    }

    @GetMapping("/setup")
    public String doSetup(Principal p , HttpSession ses) {
        String emailId = p.getName();
        UserInfo info = userInfoService.getOneUserDetailByEmailId(emailId);
        ses.setAttribute("fullinfo",info);
        ses.setAttribute("user",info.getName());
        ses.setAttribute("isAdmin", UserInfoUtils.getUserRolesInString(info.getRoles()).contains("ADMIN"));
        LOGGER.info("Session Created");
        return "redirect:/uom/register";
    }

    @GetMapping("/all")
    public String getAllUsers(Model model) {
      List<UserInfo> userInfoList =  userInfoService.getAllUsers();
      model.addAttribute("list",userInfoList);
      return "UsersDataPage";
    }

    @GetMapping("/updateuserstatus")
    public String updateStatus(@RequestParam Integer id , @RequestParam String status , Model model ) {
        userInfoService.updateUserStatus(id, status);
        model.addAttribute("message" ,"User updated Successfully");
        return "redirect:all";
    }

    @GetMapping("/profile")
    public String showUserProfile(HttpSession session , Model model ) {
        UserInfo info = (UserInfo) session.getAttribute("fullinfo");
        String roles = UserInfoUtils.getUserRolesInString(info.getRoles()).toString();
        model.addAttribute("userInfo" ,info);
        model.addAttribute("roles" ,roles);
        return "UserInfoProfilePage";
    }




}
