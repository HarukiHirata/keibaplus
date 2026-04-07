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
import com.keibaplus.webap.dto.ShuushiRegisterDto;
import com.keibaplus.webap.dto.ShuushiSearchDto;
import com.keibaplus.webap.dto.ShuushiUpdateDto;
import com.keibaplus.webap.dto.ShuushiKenshuCourseDto;
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

    @GetMapping("/shuushiitiran")
    public String shuushiitiran(Model model) {
        model.addAttribute("loginUserNo", usersService.getLoginUserNo());
        model.addAttribute("form", new ShuushiSearchDto());
        model.addAttribute("kenshuList", shuushiService.findAllKenshu());
        model.addAttribute("courseList", shuushiService.findAllCourse());
        // model.addAttribute("shuushiList", shuushiService.findAllShushiByLoginUser());
        return "shuushiitiran";
    }

    @GetMapping("/shuushihenshuu/{shuushiNo}")
    public String shuushihenshuugamen(@PathVariable Integer shuushiNo, Model model) {
        ShuushiUpdateDto dto = shuushiService.getShuushiByShuushiNo(shuushiNo);
        model.addAttribute("form", dto);
        model.addAttribute("kenshuList", shuushiService.findAllKenshu());
        model.addAttribute("courseList", shuushiService.findAllCourse());
        return "shuushihenshuu";
    }

    @PostMapping("/shuushiupdate")
    public String shuushiupdate(@ModelAttribute("form") @Valid ShuushiUpdateDto dto,
            BindingResult bindingResult,
            Model model) {
        model.addAttribute("loginUserNo", usersService.getLoginUserNo());
        if (bindingResult.hasErrors()) {
            return "shuushitouroku?error";
        }
        shuushiService.updateShuushi(dto);
        return "redirect:/top";
    }

    @GetMapping("/shuushidelete/{shuushiNo}")
    public String shuushideletegamen(@PathVariable Integer shuushiNo, Model model) {
        ShuushiKenshuCourseDto dto = shuushiService.getShuushiByShuushiNoForDelete(shuushiNo);
        model.addAttribute("shuushi", dto);
        return "shuushidelete";
    }

    @PostMapping("/shuushidelete/{shuushiNo}")
    public String shuushidelete(@PathVariable Integer shuushiNo) {
        shuushiService.deleteShuushi(shuushiNo);
        return "redirect:/shuushiitiran";
    }

    @GetMapping("/userhenshuu/{userNo}")
    public String userupdategamen(@PathVariable String userNo, Model model) {
        UsersUpdateDto dto = usersService.getUserByUserNo(userNo);
        model.addAttribute("loginUserNo", usersService.getLoginUserNo());
        model.addAttribute("form", dto);
        return "userhenshuu";
    }

    @PostMapping("/userhenshuu/{userNo}")
    public String userupdate(@ModelAttribute("form") @Valid UsersUpdateDto dto,
            BindingResult bindingResult,
            Model model) {
        model.addAttribute("loginUserNo", usersService.getLoginUserNo());
        if (bindingResult.hasErrors()) {
            return "userhenshuu?error";
        }
        usersService.updateUser(dto);
        return "redirect:/top";
    }

}
