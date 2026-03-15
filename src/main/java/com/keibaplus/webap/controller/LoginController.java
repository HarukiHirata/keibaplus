package com.keibaplus.webap.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.keibaplus.webap.entity.Users;
import com.keibaplus.webap.service.UsersService;

@Controller
public class LoginController {

    private final UsersService usersService;

    public LoginController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @GetMapping("/notlogin")
    public String getNotLogin() {
        return "notlogin";
    }

    @GetMapping("/top")
    public String top(Model model) {
        // String userId = principal.getName();
        model.addAttribute("loginUserNo", usersService.getLoginUserNo());
        model.addAttribute("loginUserId", usersService.getLoginUserId());
        model.addAttribute("loginUserMailaddress", usersService.getLoginUserMailaddress());
        return "top";
    }
}
