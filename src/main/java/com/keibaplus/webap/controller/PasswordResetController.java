package com.keibaplus.webap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.keibaplus.webap.dto.PasswordResetDto;
import com.keibaplus.webap.dto.PasswordResetRequestDto;
import com.keibaplus.webap.exception.InvalidPasswordResetTokenException;
import com.keibaplus.webap.service.PasswordResetService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.RequiredArgsConstructor;

/**
 * パスワード再設定処理関連のコントローラー
 */
@Controller
@RequiredArgsConstructor
public class PasswordResetController {

    // Beanの注入
    private final PasswordResetService passwordResetService;

    // ロガーの定義
    private static final Logger logger = LoggerFactory.getLogger(PasswordResetController.class);

    /**
     * パスワードリセット申請画面の表示
     * 
     * @param model   Modelインスタンス
     * @param request HTTPサーブレットリクエスト情報
     * @return パスワードリセット申請画面のテンプレート
     */
    @GetMapping("/password-reset-request")
    public String requestPage(Model model, HttpServletRequest request) {
        model.addAttribute("form", new PasswordResetRequestDto());
        logger.info("パスワードリセット申請画面表示 uri={}", request.getRequestURI());
        return "passwordResetRequest";
    }

    /**
     * パスワードリセット申請・メール送信処理
     * 
     * @param dto           パスワードリセット申請用DTO
     * @param bindingResult バリデーション結果
     * @return パスワードリセット申請画面のテンプレートかパスワード再設定用メール送信完了画面へのリダイレクト
     */
    @PostMapping("/password-reset-request")
    public String request(
            @ModelAttribute("form") @Valid PasswordResetRequestDto dto,
            BindingResult bindingResult) {

        // バリデーションエラーがあった場合にパスワードリセット申請画面をもう一度表示
        if (bindingResult.hasErrors()) {
            return "passwordResetRequest";
        }

        // バリデーションエラーがなければパスワード再設定用トークン発行・メール送信処理へ
        passwordResetService.requestPasswordReset(
                dto.getMailAddress());

        // パスワード再設定用メール送信完了画面へリダイレクト
        // （セキュリティ上DBに存在しないメールアドレスでも同じ画面へリダイレクト）
        return "redirect:/password-reset-mail-sent";
    }

    /**
     * パスワード再設定用メール送信完了画面の表示
     * 
     * @param request HTTPサーブレットリクエスト情報
     * @return パスワード再設定用メール送信完了画面のテンプレート
     */
    @GetMapping("/password-reset-mail-sent")
    public String passwordResetMailSentPage(HttpServletRequest request) {
        // ログ出力・テンプレートをreturn
        logger.info("パスワード再設定用メール送信完了画面表示 uri={}", request.getRequestURI());
        return "passwordResetMailSent";
    }

    /**
     * パスワード再設定画面の表示
     * 
     * @param token   パスワード再設定用トークン
     * @param model   Modelインスタンス
     * @param request HTTPサーブレットリクエスト情報
     * @return パスワード再設定画面のテンプレートかエラー画面のテンプレート
     */
    @GetMapping("/password-reset")
    public String resetPage(
            @RequestParam("token") String token,
            Model model,
            HttpServletRequest request) {

        // トークンが無効であった場合にエラー画面を表示
        if (!passwordResetService.isUsableToken(token)) {
            return "passwordResetInvalid";
        }

        // 有効なトークンであればログ出力・パスワード再設定画面表示
        logger.info("パスワード再設定画面表示 uri={}", request.getRequestURI());

        PasswordResetDto dto = new PasswordResetDto();
        dto.setToken(token);
        model.addAttribute("form", dto);

        return "passwordReset";
    }

    /**
     * パスワード変更処理
     * 
     * @param dto
     * @param bindingResult
     * @return
     */
    @PostMapping("/password-reset")
    public String reset(
            @ModelAttribute("form") @Valid PasswordResetDto dto,
            BindingResult bindingResult) {

        // バリデーションエラーがあった場合にパスワード再設定画面をもう一度表示
        if (bindingResult.hasErrors()) {
            return "passwordReset";
        }

        // バリデーションエラーがなければパスワード変更処理へ
        try {
            passwordResetService.resetPassword(
                    dto.getToken(),
                    dto.getPassword());
        } catch (InvalidPasswordResetTokenException e) {
            return "passwordResetInvalid";
        }

        return "redirect:/login?passwordReset";
    }
}