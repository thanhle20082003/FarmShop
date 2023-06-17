package com.web.farm.FarmShop.controller.site;

import com.web.farm.FarmShop.domain.Customer;
import com.web.farm.FarmShop.model.CustomerDTO;
import com.web.farm.FarmShop.model.SiteLoginDTO;
import com.web.farm.FarmShop.service.CustomerService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Controller
public class SiteLoginController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private HttpSession session;

    //site login
    @GetMapping("slogin")
    public String login (ModelMap model) {
        model.addAttribute("customer", new SiteLoginDTO());

        return "/site/customers/login";
    }

    @GetMapping("register")
    public String register (ModelMap model) {
        model.addAttribute("customer", new CustomerDTO());

        return "/site/customers/register";
    }

    @PostMapping("register")
    public ModelAndView saveOrUpdate(ModelMap model,
                                     @Valid @ModelAttribute("customer")
                                     CustomerDTO dto, BindingResult result){


//		nếu có lỗi -> nạp lại trang registration
        if (result.hasErrors()) {
            return new ModelAndView("site/customers/register");
        }

        //Tạo ra đối tượng entity
        Customer entity = new Customer();


        dto.setRegisteredDate(new Date());
        dto.setIsActive(true);

        //Copy dữ liệu từ đối tượng dto -> entity
        BeanUtils.copyProperties(dto, entity);

        //Lưu thông tin entity vào CSDL
        customerService.save(entity);


        //Hiển thị thông báo
        model.addAttribute("message", "Sign up successfully !");

        return new ModelAndView("redirect:/slogin" ,model);
    }


    @PostMapping("slogin")
    public ModelAndView login (ModelMap model,
                               @Valid @ModelAttribute("customer")
                               SiteLoginDTO dto, BindingResult result) {

        System.out.println("Login is running");
        //nếu có lỗi
//		->trả về view login
        if (result.hasErrors()) {
            return new ModelAndView("/site/customers/login", model);
        }

        //gọi phương thức login -> để về đối tượng account
        Customer customer = customerService.login(dto.getEmail(), dto.getPassword());


        //nếu kh tìm thấy account
        if (customer == null) {

            //hiển thị thông báo
            model.addAttribute("message", "Invalid email or password");

            //trả về view login
            return new ModelAndView("/site/customers/login", model);
        }


        //thiết lập thuộc tính username
        // -> người dùng đã đăng nhập vào hệ thống

        session.setAttribute("customer", customer);

        System.out.println("Customer login: " + session.getAttribute("customer"));



        //
        Object ruri = session.getAttribute("redirect-uri");
        System.out.println("ruri loginsite: " + ruri);

        //kiểm tra nếu tồn tại redirect uri
        if (ruri != null) {

            session.removeAttribute("redirect-uri");

            return new ModelAndView("redirect:" + ruri);
        }

        //forward đến
        return new ModelAndView("forward:/site", model);

    }

    @GetMapping("slogout")
    public String logout() {
        // Xóa thuộc tính 'customer' khỏi session
        session.removeAttribute("customer");

        // Chuyển hướng về trang đăng nhập
        return "redirect:/slogin";
    }
}
