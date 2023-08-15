package com.web.farm.FarmShop.controller;

import com.web.farm.FarmShop.domain.Account;
import com.web.farm.FarmShop.model.AccountDTO;
import com.web.farm.FarmShop.model.LoginDTO;
import com.web.farm.FarmShop.service.AccountService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Controller
public class LoginController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private HttpSession session;

    //login
    @GetMapping("login")
    public String login (ModelMap model) {

        model.addAttribute("account", new LoginDTO());


        return "site/customers/login";
    }

    @GetMapping("register")
    public String register (ModelMap model) {
        model.addAttribute("account", new AccountDTO());

        return "site/customers/register";
    }

    @PostMapping("register")
    public ModelAndView saveOrUpdate(ModelMap model,
                                     @Valid @ModelAttribute("account")
                                     AccountDTO dto, BindingResult result){


//		nếu có lỗi -> nạp lại trang registration
        if (result.hasErrors()) {
            return new ModelAndView("site/customers/register");
        }

        //Tạo ra đối tượng entity
        Account entity = new Account();

        dto.setRegisteredDate(new Date());
        dto.setIsActive(true);

        //Copy dữ liệu từ đối tượng dto -> entity
        BeanUtils.copyProperties(dto, entity);

        //Lưu thông tin entity vào CSDL
        accountService.save(entity);


        //Hiển thị thông báo
        model.addAttribute("message", "Sign up successfully !");

        return new ModelAndView("redirect:/login" ,model);
    }

    @PostMapping("/login/haha")
    public ModelAndView login (ModelMap model,
                               @Valid @ModelAttribute("account")
                               LoginDTO dto, BindingResult result) {
        System.out.println("Login");
        //nếu có lỗi
//		->trả về view login
        if (result.hasErrors()) {
            return new ModelAndView("/site/customers/login", model);
        }


        //gọi phương thức login -> để về đối tượng account
        Account account = accountService.login(dto.getUsername(), dto.getPassword());
        System.out.println(account.getAuthorities());

        //nếu kh tìm thấy account
        if (account == null) {

            //hiển thị thông báo
            model.addAttribute("message", "Invalid username or password");

            //trả về view login
            return new ModelAndView("/site/customers/login", model);
        }


        //thiết lập thuộc tính username
        // -> người dùng đã đăng nhập vào hệ thống

        //
        Object ruri = session.getAttribute("redirect-uri");

        //kiểm tra nếu tồn tại redirect uri
        if (ruri != null) {

            session.removeAttribute("redirect-uri");

            return new ModelAndView("redirect:" + ruri);
        }

        session.setAttribute("username", account);
        //forward đến
        return new ModelAndView("forward:/site", model);

    }
}