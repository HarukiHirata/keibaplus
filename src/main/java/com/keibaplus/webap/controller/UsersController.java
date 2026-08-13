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

import com.keibaplus.webap.common.CurrentUserProvider;
import com.keibaplus.webap.dto.UsersRegisterDto;
import com.keibaplus.webap.dto.UsersUpdateDto;
import com.keibaplus.webap.service.UsersService;

import lombok.RequiredArgsConstructor;

/**
 * ユーザー管理処理関係のコントローラー
 */
@Controller
@RequiredArgsConstructor
public class UsersController {

    // ユーザー管理処理のためにUsersServiceのインスタンスを使用
    private final UsersService usersService;
    private final CurrentUserProvider currentUserProvider;

    // ロガーの定義
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    /**
     * ユーザー登録画面の表示
     * 
     * @param model   Modelインスタンス
     * @param request HTTPサーブレットリクエスト情報
     * @return ユーザー登録画面のテンプレート
     */
    @GetMapping("/register")
    public String registerPage(Model model, HttpServletRequest request) {
        // ログ出力
        logger.info("新規登録画面表示 uri={}", request.getRequestURI());
        // modelに必要な値を設定（ユーザー登録用DTO）
        model.addAttribute("form", new UsersRegisterDto());
        // テンプレートをreturn
        return "register";
    }

    /**
     * ユーザー登録処理
     * 
     * @param dto           ユーザー登録用DTO
     * @param bindingResult バリデーション結果
     * @param model         Modelインスタンス
     * @return ユーザー登録画面のテンプレートかログイン画面へのリダイレクト
     */
    @PostMapping("/register")
    public String register(@ModelAttribute("form") @Valid UsersRegisterDto dto,
            BindingResult bindingResult,
            Model model,
            HttpServletRequest request) {
        // ユーザーIDとメールアドレスはUNIQUEであるかチェック
        if (usersService.existsByUserId(dto.getUserId())) {
            bindingResult.rejectValue("userId", "error.userId", "入力したユーザーIDは既に使用されています");
        }
        if (usersService.existsByMailAddress(dto.getMailAddress())) {
            bindingResult.rejectValue("mailAddress", "error.mailAddress", "入力したメールアドレスは既に使用されています");
        }
        // バリデーションエラーがあった場合にユーザー登録画面をもう一度表示
        if (bindingResult.hasErrors()) {
            logger.info("新規登録画面表示 uri={}", request.getRequestURI());
            return "register";
        }
        // バリデーションエラーがなければUsersServiceの登録処理
        usersService.createUser(dto);
        // 登録処理が無事完了すればログイン画面へリダイレクト
        return "redirect:/login?registered";
    }

    /**
     * ユーザー情報更新画面の表示
     * 
     * @param model   Modelインスタンス
     * @param request HTTPサーブレットリクエスト情報
     * @return ユーザー情報更新画面のテンプレート
     */
    @GetMapping("/useredit")
    public String userEditPage(Model model, HttpServletRequest request) {
        // 元データを表示するためにユーザー情報更新用DTOに更新対象のユーザーデータを格納
        UsersUpdateDto dto = usersService.getUserByUserNo();
        // modelに必要な値を設定（ログインユーザー番号・ユーザー情報更新用DTO）
        model.addAttribute("loginUserNo", dto.getUserNo());
        model.addAttribute("form", dto);
        // ログ出力・テンプレートをreturn
        logger.info("ユーザー情報変更画面表示 uri={} userNo={}", request.getRequestURI(), currentUserProvider.getLoginUserNo());
        return "useredit";
    }

    /**
     * ユーザー情報更新処理
     * 
     * @param dto           ユーザー情報更新用DTO
     * @param bindingResult バリデーション結果
     * @param model         Modelインスタンス
     * @return ユーザー情報更新画面のテンプレートかトップページ画面へのリダイレクト
     */
    @PostMapping("/useredit")
    public String userEdit(@ModelAttribute("form") @Valid UsersUpdateDto dto,
            BindingResult bindingResult,
            Model model,
            HttpServletRequest request) {
        // ユーザーIDとメールアドレスはUNIQUEであるかチェック
        if (usersService.existsByUserIdAndUserNo(dto.getUserId(), currentUserProvider.getLoginUserNo())) {
            bindingResult.rejectValue("userId", "error.userId", "入力したユーザーIDは既に使用されています");
        }
        if (usersService.existsByMailAddressAndUserNo(dto.getMailAddress(), currentUserProvider.getLoginUserNo())) {
            bindingResult.rejectValue("mailAddress", "error.mailAddress", "入力したメールアドレスは既に使用されています");
        }
        // バリデーションエラーがあった場合にユーザー情報更新画面をもう一度表示
        if (bindingResult.hasErrors()) {
            logger.info("ユーザー情報変更画面表示 uri={} userNo={}", request.getRequestURI(), currentUserProvider.getLoginUserNo());
            return "useredit";
        }
        // バリデーションエラーがなければUsersServiceの更新処理
        usersService.updateUser(dto);
        // 登録処理が無事完了すればトップページ画面へリダイレクト
        return "redirect:/top";
    }

    /**
     * ユーザー削除画面の表示
     * 
     * @param model   Modelインスタンス
     * @param request HTTPサーブレットリクエスト情報
     * @return ユーザー削除画面のテンプレート
     */
    @GetMapping("/userdelete")
    public String userDeletePage(Model model, HttpServletRequest request) {
        // modelに必要な値を設定（ログインユーザーID）
        model.addAttribute("loginUserId", currentUserProvider.getLoginUserId());
        // ログ出力・テンプレートをreturn
        logger.info("ユーザー削除画面表示 uri={} userNo={}", request.getRequestURI(), currentUserProvider.getLoginUserNo());
        return "userdelete";
    }

    /**
     * ユーザー削除処理
     * 
     * @param request  HTTPサーブレットリクエスト情報
     * @param response HTTPサーブレットレスポンス情報
     * @return ユーザー削除成功画面へのリダイレクト
     */
    @PostMapping("/userdelete")
    public String userDelete(HttpServletRequest request, HttpServletResponse response) {
        // UsersServiceの削除処理
        usersService.deleteUser(currentUserProvider.getLoginUserNo());
        // Spring Securityの認証情報が残ってしまうと登録ユーザーの画面が使えてしまうためログアウト時と同じ処理を実行
        // 認証情報を取得
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 認証情報をクリア
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        // ユーザー削除成功画面へのリダイレクトをreturn
        return "redirect:/userdeletesuccess";
    }

    /**
     * ユーザー削除成功画面の表示
     * 
     * @param request HTTPサーブレットリクエスト情報
     * @return ユーザー削除成功画面のテンプレート
     */
    @GetMapping("/userdeletesuccess")
    public String userDeleteSuccessPage(HttpServletRequest request) {
        // ログ出力・テンプレートをreturn
        logger.info("ユーザー削除成功画面表示 uri={}", request.getRequestURI());
        return "userdeletesuccess";
    }

}
