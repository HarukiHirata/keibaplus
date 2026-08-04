package com.keibaplus.webap.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.validation.BindingResult;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.keibaplus.webap.dto.ShuushiRegisterDto;
import com.keibaplus.webap.dto.ShuushiSearchDto;
import com.keibaplus.webap.dto.ShuushiUpdateDto;
import com.keibaplus.webap.common.LoginUserInfo;
import com.keibaplus.webap.dto.ShuushiKenshuCourseDto;
import com.keibaplus.webap.service.ShuushiService;

/**
 * 収支管理処理関係のコントローラー
 */
@Controller
public class ShuushiController {

    // 収支管理処理のためにShuushiServiceのインスタンスを使用
    private final ShuushiService shuushiService;

    // ロガーの定義
    private static final Logger logger = LoggerFactory.getLogger(ShuushiController.class);

    // コンストラクタ
    public ShuushiController(ShuushiService shuushiService) {
        this.shuushiService = shuushiService;
    }

    /**
     * 収支登録画面の表示
     * 
     * @param model Modelインスタンス
     * @return 収支登録画面のテンプレート
     */
    @GetMapping("/shuushiregister")
    public String shuushiRegisterPage(Model model, HttpServletRequest request) {
        // modelに必要な値を設定（ログインユーザー情報・収支登録用DTO・券種一覧・コース一覧）
        model.addAttribute("loginUserNo", LoginUserInfo.getLoginUserNo());
        model.addAttribute("form", new ShuushiRegisterDto());
        model.addAttribute("kenshuList", shuushiService.findAllKenshu());
        model.addAttribute("courseList", shuushiService.findAllCourse());
        // ログ出力・テンプレートをreturn
        logger.info("収支登録画面表示 uri={} userNo={}", request.getRequestURI(), LoginUserInfo.getLoginUserNo());
        return "shuushiregister";
    }

    /**
     * 収支登録処理
     * 
     * @param dto           収支登録用DTO
     * @param bindingResult バリデーション結果
     * @param model         Modelインスタンス
     * @return 収支登録画面のテンプレートかトップページ画面へのリダイレクト
     */
    @PostMapping("/shuushiregister")
    public String shuushiRegister(@ModelAttribute("form") @Valid ShuushiRegisterDto dto,
            BindingResult bindingResult,
            Model model,
            HttpServletRequest request) {
        // バリデーションエラーがあった場合に収支登録画面をもう一度表示
        if (bindingResult.hasErrors()) {
            model.addAttribute("kenshuList", shuushiService.findAllKenshu());
            model.addAttribute("courseList", shuushiService.findAllCourse());
            logger.info("収支登録画面表示 uri={} userNo={}", request.getRequestURI(), LoginUserInfo.getLoginUserNo());
            return "shuushiregister";
        }
        // バリデーションエラーがなければShuushiServiceの登録処理
        shuushiService.createShuushi(dto);
        // 登録処理が無事完了すればトップページ画面へリダイレクト
        return "redirect:/top";
    }

    /**
     * 収支一覧画面の表示
     * 
     * @param model Modelインスタンス
     * @return 収支一覧画面のテンプレート
     */
    @GetMapping("/shuushilist")
    public String shuushiList(Model model, HttpServletRequest request) {
        // modelに必要な値を設定（ログインユーザー情報・収支検索用DTO・券種一覧・コース一覧）
        model.addAttribute("loginUserNo", LoginUserInfo.getLoginUserNo());
        model.addAttribute("loginUserId", LoginUserInfo.getLoginUserId());
        model.addAttribute("form", new ShuushiSearchDto());
        model.addAttribute("kenshuList", shuushiService.findAllKenshu());
        model.addAttribute("courseList", shuushiService.findAllCourse());
        // ログ出力・テンプレートをreturn
        logger.info("収支一覧画面表示 uri={} userNo={}", request.getRequestURI(), LoginUserInfo.getLoginUserNo());
        return "shuushilist";
    }

    /**
     * 収支編集画面の表示
     * 
     * @param shuushiNo 収支No
     * @param model     Modelインスタンス
     * @return 収支編集画面のテンプレート
     */
    @GetMapping("/shuushiedit/{shuushiNo}")
    public String shuushiEditPage(@PathVariable Integer shuushiNo, Model model, HttpServletRequest request) {
        // 元データを表示するために収支更新用DTOに更新対象の収支データを格納
        ShuushiUpdateDto dto = shuushiService.getShuushiByShuushiNo(shuushiNo);
        // modelに必要な値を設定（収支更新用DTO・券種一覧・コース一覧）
        model.addAttribute("form", dto);
        model.addAttribute("kenshuList", shuushiService.findAllKenshu());
        model.addAttribute("courseList", shuushiService.findAllCourse());
        // ログ出力・テンプレートをreturn
        logger.info("収支編集画面表示 uri={} userNo={} shuushiNo={}", request.getRequestURI(), LoginUserInfo.getLoginUserNo(),
                shuushiNo);
        return "shuushiedit";
    }

    /**
     * 収支更新処理
     * 
     * @param dto           収支更新用DTO
     * @param bindingResult バリデーション結果
     * @param model         Modelインスタンス
     * @return 収支編集画面のテンプレートかトップページ画面へのリダイレクト
     */
    @PostMapping("/shuushiedit/{shuushiNo}")
    public String shuushiEdit(@ModelAttribute("form") @Valid ShuushiUpdateDto dto,
            BindingResult bindingResult,
            Model model,
            HttpServletRequest request) {
        // バリデーションエラーがあった場合に収支更新画面をもう一度表示
        if (bindingResult.hasErrors()) {
            // 再表示のためmodelに必要な値を設定（収支No・券種一覧・コース一覧）
            model.addAttribute("shuushiNo", dto.getShuushiNo());
            model.addAttribute("kenshuList", shuushiService.findAllKenshu());
            model.addAttribute("courseList", shuushiService.findAllCourse());
            logger.info("収支編集画面表示 uri={} userNo={} shuushiNo={}", request.getRequestURI(),
                    LoginUserInfo.getLoginUserNo(),
                    dto.getShuushiNo());
            return "shuushiedit";
        }
        // バリデーションエラーがなければShuushiServiceの更新処理
        shuushiService.updateShuushi(dto);
        // 更新処理が無事完了すればトップページ画面へリダイレクト
        return "redirect:/top";
    }

    /**
     * 収支削除画面の表示
     * 
     * @param shuushiNo 収支No
     * @param model     Modelインスタンス
     * @return 収支削除画面のテンプレート
     */
    @GetMapping("/shuushidelete/{shuushiNo}")
    public String shuushiDeletePage(@PathVariable Integer shuushiNo, Model model, HttpServletRequest request) {
        // 元データを表示するために収支削除用DTOに削除対象の収支データを格納
        ShuushiKenshuCourseDto dto = shuushiService.getShuushiByShuushiNoForDelete(shuushiNo);
        // modelに必要な値を設定（収支データ）
        model.addAttribute("shuushi", dto);
        // ログ出力・テンプレートをreturn
        logger.info("収支削除画面表示 uri={} userNo={} shuushiNo={}", request.getRequestURI(), LoginUserInfo.getLoginUserNo(),
                shuushiNo);
        return "shuushidelete";
    }

    /**
     * 収支削除処理
     * 
     * @param shuushiNo 収支No
     * @return 収支一覧画面へのリダイレクト
     */
    @PostMapping("/shuushidelete/{shuushiNo}")
    public String shuushiDelete(@PathVariable Integer shuushiNo) {
        // ShuushiServiceの削除処理
        shuushiService.deleteShuushi(shuushiNo);
        // 収支一覧画面へのリダイレクトをreturn
        return "redirect:/shuushilist";
    }

}
