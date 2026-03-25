package com.keibaplus.webap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.keibaplus.webap.dto.ShuushiSearchDto;
import com.keibaplus.webap.service.ShuushiService;
import com.keibaplus.webap.service.UsersService;

@Controller
public class LoginController {

    private final UsersService usersService;
    private final ShuushiService shuushiService;

    public LoginController(UsersService usersService, ShuushiService shuushiService) {
        this.usersService = usersService;
        this.shuushiService = shuushiService;
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
        model.addAttribute("loginUserNo", usersService.getLoginUserNo());
        model.addAttribute("loginUserId", usersService.getLoginUserId());
        model.addAttribute("loginUserMailaddress", usersService.getLoginUserMailaddress());
        model.addAttribute("form", new ShuushiSearchDto());
        model.addAttribute("kenshuList", shuushiService.findAllKenshu());
        model.addAttribute("courseList", shuushiService.findAllCourse());
        return "top";
    }
}
