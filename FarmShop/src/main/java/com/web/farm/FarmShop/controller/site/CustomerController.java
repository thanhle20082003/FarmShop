package com.web.farm.FarmShop.controller.site;

import com.web.farm.FarmShop.domain.Customer;
import com.web.farm.FarmShop.model.CustomerDTO;
import com.web.farm.FarmShop.service.CustomerService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("site/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    HttpSession session;
    @GetMapping("information")
    public String list(ModelMap model) {
        Customer customer = (Customer) session.getAttribute("customer");

        model.addAttribute("customer", customer);

        return "site/customers/information";
    }

    @PostMapping("information")
    public ModelAndView ModelAndView(ModelMap model, @Valid @ModelAttribute("customer")CustomerDTO dto, BindingResult result) {
        if(result.hasErrors()) {
            return new ModelAndView("site/customers/information", model);
        }

        Customer entity = new Customer();

        BeanUtils.copyProperties(dto, entity);

        customerService.save(entity);

        model.addAttribute("message", "Update successfully");

        return new ModelAndView("forward:/site/customers/information", model);
    }
}
