package com.web.farm.FarmShop.controller.site;

import com.web.farm.FarmShop.domain.Account;
import com.web.farm.FarmShop.model.AccountDTO;
import com.web.farm.FarmShop.service.AccountService;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("site/customers")
public class CustomerController {

    @Autowired
    private AccountService accountService;

    @Autowired
    HttpSession session;
    @GetMapping("information")
    public String list(ModelMap model) {
        Account customer = (Account) session.getAttribute("customer");

        model.addAttribute("customer", customer);

        return "site/customers/information";
    }

    @PostMapping("information")
    public ModelAndView ModelAndView(ModelMap model, @Valid @ModelAttribute("customer") AccountDTO dto, BindingResult result) {
        if(result.hasErrors()) {
            return new ModelAndView("site/customers/information", model);
        }

        Account entity = new Account();

        BeanUtils.copyProperties(dto, entity);

        accountService.save(entity);

        model.addAttribute("message", "Update successfully");

        return new ModelAndView("forward:/site/customers/information", model);
    }
}
