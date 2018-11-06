package com.apap.tutorial6.controller;

import com.apap.tutorial6.model.UserRoleModel;
import com.apap.tutorial6.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user")
public class UserRoleController {
    @Autowired
    private UserRoleService userService;

    @RequestMapping(value= "/addUser", method = RequestMethod.POST)
    private String addUserSubmit(@ModelAttribute UserRoleModel user){
        userService.addUser(user);
        return "home";
    }

    @RequestMapping(value= "/userUpdate", method = RequestMethod.GET)
    private String updateUser(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserRoleModel user = userService.getUserRoleModelByUserName(authentication.getName());
        model.addAttribute("user", user);
        return "update-password";
    }

    @RequestMapping(value= "/userUpdate", method = RequestMethod.POST)
    private String updateUserSubmit(@ModelAttribute UserRoleModel user, Model model){
        UserRoleModel archive = userService.getUserRoleModelByUserName(user.getUsername());
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        if(encoder.matches(user.getOldpassword(),archive.getPassword())){
            archive.setPassword(user.getPassword());
            userService.addUser(archive);
            return "home";
        }else{
            model.addAttribute("user", user);
            model.addAttribute("errorMessage", "Wrong Old Password");
            return "update-password";
        }
    }
}
