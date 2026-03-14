package com.keibaplus.webap.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.validation.BindingResult;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import jakarta.validation.Valid;

import com.keibaplus.webap.dto.UsersRegisterDto;
import com.keibaplus.webap.service.UsersService;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/shuushitouroku")
    public String shuushitouroku(Principal principal, Model model) {
        String userId = principal.getName();
        model.addAttribute("loginUser", usersService.getLoginUser(userId));
        return "shuushitouroku";
    }

}
