package com.keibaplus.webap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    @GetMapping("/notlogin")
    public String getNotLogin() {
        return "notlogin";
    }
}
