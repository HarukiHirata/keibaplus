package com.keibaplus.webap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * LP画面関係のコントローラー
 */
@Controller
public class WelcomeController {
    /**
     * LP画面の表示
     * 
     * @return LP画面のテンプレート
     */
    @GetMapping("/welcome")
    public String welcome() {
        // テンプレートをreturn
        return "welcome";
    }
}
