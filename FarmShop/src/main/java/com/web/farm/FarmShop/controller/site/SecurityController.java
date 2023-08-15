package com.web.farm.FarmShop.controller.site;

import com.web.farm.FarmShop.domain.Account;
import com.web.farm.FarmShop.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class SecurityController {
    @Autowired
    private HttpSession session;

    @Autowired
    private AccountService accountService;

    @RequestMapping("/login/success")
    public String success(Model model, Authentication authentication){
        String username = authentication.getName();
        Optional<Account> account = accountService.findById(username);
        session.setAttribute("username", account.get());
        model.addAttribute("message","Success");
        return "forward:/site";
    }
    @RequestMapping("/login/error")
    public String error(Model model){
        model.addAttribute("message","Invalid username or password");
        return "forward:/login";
    }
}
