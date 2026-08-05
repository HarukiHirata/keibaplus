package com.keibaplus.webap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.keibaplus.webap.common.CurrentUserProvider;
import com.keibaplus.webap.dto.ShuushiSearchDto;
import com.keibaplus.webap.service.MasterDataService;

import jakarta.servlet.http.HttpServletRequest;

/**
 * ログイン・認証処理関係のコントローラー
 */
@Controller
public class LoginController {

    // 券種やコースの一覧を取得するためにShuushiServiceのインスタンスを使用
    private final CurrentUserProvider currentUserProvider;
    private final MasterDataService masterDataService;

    // ロガーの定義
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    // コンストラクタ
    public LoginController(CurrentUserProvider currentUserProvider, MasterDataService masterDataService) {
        this.currentUserProvider = currentUserProvider;
        this.masterDataService = masterDataService;
    }

    /**
     * ログイン画面の表示
     * 
     * @param request HTTPサーブレットリクエスト情報
     * @return ログイン画面のテンプレート
     */
    @GetMapping("/login")
    public String getLogin(HttpServletRequest request) {
        // ログ出力・テンプレートをreturn
        logger.info("ログイン画面表示 uri={}", request.getRequestURI());
        return "login";
    }

    /**
     * 未ログインエラー画面の表示
     * 
     * @param request HTTPサーブレットリクエスト情報
     * @return 未ログインエラー画面のテンプレート
     */
    @GetMapping("/notlogin")
    public String getNotLogin(HttpServletRequest request) {
        // ログ出力・テンプレートをreturn
        logger.info("未ログインエラー画面表示 uri={}", request.getRequestURI());
        return "notlogin";
    }

    /**
     * 無認可アクセス画面の表示
     * 
     * @param request HTTPサーブレットリクエスト情報
     * @return 無認可アクセスエラー画面のテンプレート
     */
    @GetMapping("/unauthorizedAccess")
    public String getUnauthorizedAccess(HttpServletRequest request) {
        // ログ出力・テンプレートをreturn
        logger.info("無認可アクセス画面表示 uri={}", request.getRequestURI());
        return "unauthorizedAccess";
    }

    /**
     * トップページ画面の表示
     * 
     * @param model   Modelインスタンス
     * @param request HTTPサーブレットリクエスト情報
     * @return トップページ画面のテンプレート
     */
    @GetMapping("/top")
    public String top(Model model, HttpServletRequest request) {
        // ログ出力
        logger.info("トップ画面表示 uri={} userNo={}", request.getRequestURI(), currentUserProvider.getLoginUserNo());
        // modelに必要な値を設定（ログインユーザー情報・収支検索用DTO・券種一覧・コース一覧）
        model.addAttribute("loginUserNo", currentUserProvider.getLoginUserNo());
        model.addAttribute("loginUserId", currentUserProvider.getLoginUserId());
        model.addAttribute("form", new ShuushiSearchDto());
        model.addAttribute("kenshuList", masterDataService.findAllKenshu());
        model.addAttribute("courseList", masterDataService.findAllCourse());
        // テンプレートをreturn
        return "top";
    }
}
