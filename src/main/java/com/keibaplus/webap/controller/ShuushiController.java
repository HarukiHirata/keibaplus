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
public class ShuushiController {

    private final UsersService usersService;
    private final ShuushiService shuushiService;

    public ShuushiController(UsersService usersService, ShuushiService shuushiService) {
        this.usersService = usersService;
        this.shuushiService = shuushiService;
    }

    @GetMapping("/shuushiregister")
    public String shuushiRegisterPage(Model model) {
        model.addAttribute("form", new ShuushiRegisterDto());
        model.addAttribute("loginUserNo", usersService.getLoginUserNo());
        model.addAttribute("kenshuList", shuushiService.findAllKenshu());
        model.addAttribute("courseList", shuushiService.findAllCourse());
        return "shuushiregister";
    }

    @PostMapping("/shuushiregister")
    public String shuushiRegister(@ModelAttribute("form") @Valid ShuushiRegisterDto dto,
            BindingResult bindingResult,
            Model model) {
        model.addAttribute("loginUserNo", usersService.getLoginUserNo());
        if (bindingResult.hasErrors()) {
            return "shuushiregister?error";
        }
        shuushiService.createShuushi(dto);
        return "redirect:/top";
    }

    @GetMapping("/shuushilist")
    public String shuushiList(Model model) {
        model.addAttribute("loginUserNo", usersService.getLoginUserNo());
        model.addAttribute("form", new ShuushiSearchDto());
        model.addAttribute("kenshuList", shuushiService.findAllKenshu());
        model.addAttribute("courseList", shuushiService.findAllCourse());
        // model.addAttribute("shuushiList", shuushiService.findAllShushiByLoginUser());
        return "shuushilist";
    }

    @GetMapping("/shuushiedit/{shuushiNo}")
    public String shuushiEditPage(@PathVariable Integer shuushiNo, Model model) {
        ShuushiUpdateDto dto = shuushiService.getShuushiByShuushiNo(shuushiNo);
        model.addAttribute("form", dto);
        model.addAttribute("loginUserNo", usersService.getLoginUserNo());
        model.addAttribute("kenshuList", shuushiService.findAllKenshu());
        model.addAttribute("courseList", shuushiService.findAllCourse());
        return "shuushiedit";
    }

    @PostMapping("/shuushiedit/{shuushiNo}")
    public String shuushiEdit(@ModelAttribute("form") @Valid ShuushiUpdateDto dto,
            BindingResult bindingResult,
            Model model) {
        model.addAttribute("loginUserNo", usersService.getLoginUserNo());
        if (bindingResult.hasErrors()) {
            return "shuushiedit?error";
        }
        shuushiService.updateShuushi(dto);
        return "redirect:/top";
    }

    @GetMapping("/shuushidelete/{shuushiNo}")
    public String shuushiDeletePage(@PathVariable Integer shuushiNo, Model model) {
        ShuushiKenshuCourseDto dto = shuushiService.getShuushiByShuushiNoForDelete(shuushiNo);
        model.addAttribute("shuushi", dto);
        model.addAttribute("loginUserNo", usersService.getLoginUserNo());
        return "shuushidelete";
    }

    @PostMapping("/shuushidelete/{shuushiNo}")
    public String shuushiDelete(@PathVariable Integer shuushiNo) {
        shuushiService.deleteShuushi(shuushiNo);
        return "redirect:/shuushilist";
    }

}
