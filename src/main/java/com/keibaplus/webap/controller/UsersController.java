package com.keibaplus.webap.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.validation.BindingResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
        if (usersService.existsByUserId(dto.getUserId())) {
            bindingResult.rejectValue("userId", "error.userId", "入力したユーザーIDは既に使用されています");
        }
        if (usersService.existsByMailAddress(dto.getMailAddress())) {
            bindingResult.rejectValue("mailAddress", "error.mailAddress", "入力したメールアドレスは既に使用されています");
        }
        if (bindingResult.hasErrors()) {
            return "register";
        }
        usersService.createUser(dto);
        return "redirect:/login?registered";
    }

    @GetMapping("/useredit")
    public String userEditPage(Model model, HttpServletRequest request) {
        UsersUpdateDto dto = usersService.getUserByUserNo();
        model.addAttribute("loginUserNo", dto.getUserNo());
        model.addAttribute("form", dto);
        logger.info("ユーザー情報変更画面表示 uri={} userNo={}", request.getRequestURI(), usersService.getLoginUserNo());
        return "useredit";
    }

    @PostMapping("/useredit")
    public String userEdit(@ModelAttribute("form") @Valid UsersUpdateDto dto,
            BindingResult bindingResult,
            Model model) {
        if (usersService.existsByUserIdAndUserNo(dto.getUserId(), usersService.getLoginUserNo())) {
            bindingResult.rejectValue("userId", "error.userId", "入力したユーザーIDは既に使用されています");
        }
        if (usersService.existsByMailAddressAndUserNo(dto.getMailAddress(), usersService.getLoginUserNo())) {
            bindingResult.rejectValue("mailAddress", "error.mailAddress", "入力したメールアドレスは既に使用されています");
        }
        if (bindingResult.hasErrors()) {
            return "useredit";
        }
        usersService.updateUser(dto);
        return "redirect:/top";
    }

    @GetMapping("/userdelete")
    public String userDeletePage(Model model, HttpServletRequest request) {
        model.addAttribute("loginUserId", usersService.getLoginUserId());
        logger.info("ユーザー削除画面表示 uri={} userNo={}", request.getRequestURI(), usersService.getLoginUserNo());
        return "userdelete";
    }

    @PostMapping("/userdelete")
    public String userDelete(HttpServletRequest request, HttpServletResponse response) {
        usersService.deleteUser(usersService.getLoginUserNo());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/userdeletesuccess";
    }

    @GetMapping("/userdeletesuccess")
    public String userDeleteSuccessPage(HttpServletRequest request) {
        logger.info("ユーザー削除成功画面表示 uri={}", request.getRequestURI());
        return "userdeletesuccess";
    }

}
