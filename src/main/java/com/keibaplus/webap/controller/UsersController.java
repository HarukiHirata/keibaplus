package com.keibaplus.webap.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.validation.BindingResult;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import jakarta.validation.Valid;

import com.keibaplus.webap.dto.UsersRegisterDto;
import com.keibaplus.webap.dto.UsersUpdateDto;
import com.keibaplus.webap.service.UsersService;

@Controller
public class UsersController {
    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("form", new UsersRegisterDto());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("form") @Valid UsersRegisterDto dto,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        usersService.createUser(dto);
        return "redirect:/login?registered";
    }

    @GetMapping("/useredit/{userNo}")
    public String userEditPage(@PathVariable String userNo, Model model) {
        UsersUpdateDto dto = usersService.getUserByUserNo(userNo);
        model.addAttribute("loginUserNo", usersService.getLoginUserNo());
        model.addAttribute("form", dto);
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
