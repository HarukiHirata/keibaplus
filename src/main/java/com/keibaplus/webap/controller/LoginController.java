package com.keibaplus.webap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.keibaplus.webap.dto.ShuushiSearchDto;
import com.keibaplus.webap.service.ShuushiService;
import com.keibaplus.webap.service.UsersService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    private final UsersService usersService;
    private final ShuushiService shuushiService;
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    public LoginController(UsersService usersService, ShuushiService shuushiService) {
        this.usersService = usersService;
        this.shuushiService = shuushiService;
    }

    @GetMapping("/login")
    public String getLogin(HttpServletRequest request) {
        logger.info("ログイン画面表示 uri={}", request.getRequestURI());
        return "login";
    }

    @GetMapping("/notlogin")
    public String getNotLogin() {
        return "notlogin";
    }

    @GetMapping("/top")
    public String top(Model model, HttpServletRequest request) {
        logger.info("トップ画面表示 uri={} userNo={}", request.getRequestURI(), usersService.getLoginUserNo());
        model.addAttribute("loginUserNo", usersService.getLoginUserNo());
        model.addAttribute("loginUserId", usersService.getLoginUserId());
        model.addAttribute("loginUserMailaddress", usersService.getLoginUserMailaddress());
        model.addAttribute("form", new ShuushiSearchDto());
        model.addAttribute("kenshuList", shuushiService.findAllKenshu());
        model.addAttribute("courseList", shuushiService.findAllCourse());
        return "top";
    }
}
