package com.web.farm.FarmShop.controller.admin;

import com.web.farm.FarmShop.domain.Account;
import com.web.farm.FarmShop.model.AdminLoginDTO;
import com.web.farm.FarmShop.service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminLoginController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private HttpSession session;

    @GetMapping("alogin")
    public String login(ModelMap model) {
        model.addAttribute("account", new AdminLoginDTO());
        return "admin/accounts/login";
    }

    @PostMapping("alogin")
    public ModelAndView login(ModelMap model, @Valid @ModelAttribute("account") AdminLoginDTO dto, BindingResult result, HttpServletRequest request) {
        //nếu có lỗi
//		->trả về view login
        if (result.hasErrors()) {
            return new ModelAndView("/admin/accounts/login", model);
        }

        //gọi phương thức login -> để về đối tượng account
        Account account = accountService.login(dto.getUsername(), dto.getPassword());


        //nếu kh tìm thấy account
        if (account == null) {

            //hiển thị thông báo
            model.addAttribute("message", "Invalid username or password");

            //trả về view login
            return new ModelAndView("admin/accounts/login", model);
        }


        //thiết lập thuộc tính username
        // -> người dùng đã đăng nhập vào hệ thống

        session.setAttribute("username", account);

        //
        Object ruri = session.getAttribute("redirect-uri");
        System.out.println("ruri login = " + ruri);

        //kiểm tra nếu tồn tại redirect uri
        if (ruri != null) {

            session.removeAttribute("redirect-uri");

            return new ModelAndView("redirect:" + ruri);
        }
        //forward đến
        return new ModelAndView("forward:/admin/categories", model);

    }

    @GetMapping("alogout")
    public String logout(HttpSession session) {
        // Xóa thuộc tính 'username' khỏi session
        session.removeAttribute("username");

        // Chuyển hướng về trang đăng nhập
        return "redirect:/alogin";
    }
}
