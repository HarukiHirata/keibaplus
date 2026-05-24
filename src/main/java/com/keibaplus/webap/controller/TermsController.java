package com.keibaplus.webap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 利用規約・プライバシーポリシー画面関係のコントローラー
 */
@Controller
public class TermsController {

    /**
     * プライバシーポリシー画面の表示
     * 
     * @return プライバシーポリシー画面のテンプレート
     */
    @GetMapping("/privacyPolicy")
    public String privacyPolicy() {
        // テンプレートをreturn
        return "privacyPolicy";
    }

    /**
     * 利用規約画面の表示
     * 
     * @return 利用規約画面のテンプレート
     */
    @GetMapping("/termsOfUse")
    public String termsOfUse() {
        // テンプレートをreturn
        return "termsOfUse";
    }
}
