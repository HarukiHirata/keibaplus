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
import com.keibaplus.webap.dto.ShuushiRegisterDto;
import com.keibaplus.webap.service.ShuushiService;
import com.keibaplus.webap.service.UsersService;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UsersController {
    private final UsersService usersService;
    private final ShuushiService shuushiService;

    public UsersController(UsersService usersService, ShuushiService shuushiService) {
        this.usersService = usersService;
        this.shuushiService = shuushiService;
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
    public String shuushitourokugamen(Model model) {
        model.addAttribute("form", new ShuushiRegisterDto());
        model.addAttribute("loginUserNo", usersService.getLoginUserNo());
        model.addAttribute("kenshuList", shuushiService.findAllKenshu());
        model.addAttribute("courseList", shuushiService.findAllCourse());
        return "shuushitouroku";
    }

    @PostMapping("/shuushitouroku")
    public String shuushitouroku(@ModelAttribute("form") @Valid ShuushiRegisterDto dto,
            BindingResult bindingResult,
            Model model) {
        model.addAttribute("loginUserNo", usersService.getLoginUserNo());
        if (bindingResult.hasErrors()) {
            return "shuushitouroku?error";
        }
        shuushiService.createShuushi(dto);
        return "redirect:/top";
    }
}
