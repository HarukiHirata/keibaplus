package com.keibaplus.webap.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.validation.BindingResult;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.keibaplus.webap.dto.UsersRegisterDto;
import com.keibaplus.webap.dto.UsersUpdateDto;
import com.keibaplus.webap.service.UsersService;

@Controller
public class UsersController {
    private final UsersService usersService;

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/register")
    public String registerPage(Model model, HttpServletRequest request) {
        logger.info("新規登録画面表示 uri={}", request.getRequestURI());
        model.addAttribute("form", new UsersRegisterDto());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("form") @Valid UsersRegisterDto dto,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "register?error";
        }
        usersService.createUser(dto);
        return "redirect:/login?registered";
    }

    @GetMapping("/useredit/{userNo}")
    public String userEditPage(@PathVariable String userNo, Model model, HttpServletRequest request) {
        UsersUpdateDto dto = usersService.getUserByUserNo(userNo);
        model.addAttribute("loginUserNo", usersService.getLoginUserNo());
        model.addAttribute("form", dto);
        logger.info("ユーザー情報変更画面表示 uri={} userNo={}", request.getRequestURI(), usersService.getLoginUserNo());
        return "useredit";
    }

    @PostMapping("/useredit/{userNo}")
    public String userEdit(@ModelAttribute("form") @Valid UsersUpdateDto dto,
            BindingResult bindingResult,
            Model model) {
        model.addAttribute("loginUserNo", usersService.getLoginUserNo());
        if (bindingResult.hasErrors()) {
            return "useredit?error";
        }
        usersService.updateUser(dto);
        return "redirect:/top";
    }

}
