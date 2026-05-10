package com.keibaplus.webap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TermsController {

    @GetMapping("/privacyPolicy")
    public String privacyPolicy() {
        return "privacyPolicy";
    }

    @GetMapping("/termsOfUse")
    public String termsOfUse() {
        return "termsOfUse";
    }
}
