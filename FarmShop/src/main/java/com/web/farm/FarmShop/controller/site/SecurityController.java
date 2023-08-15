package com.web.farm.FarmShop.controller.site;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

public class SecurityController {
    @RequestMapping("/login/success")
    public String success(Model model){
        model.addAttribute("message","Success");
        return "forward:/site";
    }
    @RequestMapping("/login/error")
    public String error(Model model){
        model.addAttribute("message","Invalid username or password");
        return "forward:/login";
    }
}
