package com.keibaplus.webap.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.keibaplus.webap.dto.UsersResponseDto;
import com.keibaplus.webap.dto.UsersUpdateDto;
import com.keibaplus.webap.common.CommonConst;
import com.keibaplus.webap.common.CurrentUserProvider;
import com.keibaplus.webap.dto.UsersRegisterDto;
import com.keibaplus.webap.entity.Users;
import com.keibaplus.webap.entity.Saiban;
import com.keibaplus.webap.repository.UsersRepository;
import com.keibaplus.webap.repository.SaibanRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

/**
 * ユーザーテーブル関連のService
 */
@Service
public class UsersService {

        // 必要なrepositoryのインスタンスを使用
        private final UsersRepository usersRepository;
        private final SaibanService saibanService;
        private final PasswordEncoder passwordEncoder;
        private final LoginSessionService loginSessionService;
        private final CurrentUserProvider currentUserProvider;
        // ロガーの定義
        private static final Logger logger = LoggerFactory.getLogger(UsersService.class);

        // コンストラクタ
        public UsersService(UsersRepository usersRepository, SaibanService saibanService,
                        PasswordEncoder passwordEncoder, LoginSessionService loginSessionService,
                        CurrentUserProvider currentUserProvider) {
                this.usersRepository = usersRepository;
                this.saibanService = saibanService;
                this.passwordEncoder = passwordEncoder;
                this.loginSessionService = loginSessionService;
                this.currentUserProvider = currentUserProvider;
        }

        /**
         * ユーザー登録処理
         * 
         * @param dto ユーザー登録用DTO
         */
        @Transactional
        public void createUser(UsersRegisterDto dto) {
                try {
                        // ユーザーの登録日時を登録するために現在日時を取得
                        LocalDateTime now = LocalDateTime.now();

                        // ユーザー番号登録のために採番テーブルの値を取得・採番テーブルをこの段階で更新
                        String registerUserNo = saibanService.issueNextNo(CommonConst.USERS_TABLE_NAME);
                        // ユーザーエンティティを用いてユーザーの入力値を登録
                        Users user = new Users(
                                        registerUserNo,
                                        dto.getUserId(),
                                        passwordEncoder.encode(dto.getPassword()),
                                        dto.getMailAddress(),
                                        CommonConst.DEL_FLG_ACTIVE,
                                        now,
                                        now);
                        usersRepository.registerUser(
                                        user.getUserNo(),
                                        user.getUserId(),
                                        user.getPassword(),
                                        user.getMailAddress(),
                                        user.getDelFlg(),
                                        user.getInsDate(),
                                        user.getUpdDate());

                        // ログ出力
                        logger.info("ユーザー登録成功 userNo={}", user.getUserNo());

                } catch (Exception e) {
                        // 失敗した場合はログ出力・exceptionをthrow
                        logger.error("ユーザー登録失敗", e);
                        throw new RuntimeException("ユーザー登録処理でエラーが発生しました", e);
                }
        }

        /**
         * ユーザーID存在チェック
         * 
         * @param userId ユーザーID
         * @return 入力されたユーザーIDがある場合はtrue、そうでない場合はfalse
         */
        public boolean existsByUserId(String userId) {
                // ユーザーIDの重複がないようにするために入力されたユーザーIDが既にある場合はtrueを返却
                if (usersRepository.existsByUserId(userId, CommonConst.DEL_FLG_ACTIVE)) {
                        return true;
                } else {
                        return false;
                }
        }

        /**
         * メールアドレス存在チェック
         * 
         * @param mailAddress メールアドレス
         * @return 入力されたメールアドレスがある場合はtrue、そうでない場合はfalse
         */
        public boolean existsByMailAddress(String mailAddress) {
                // メールアドレスの重複がないようにするために入力されたメールアドレスが既にある場合はtrueを返却
                if (usersRepository.existsByMailAddress(mailAddress, CommonConst.DEL_FLG_ACTIVE)) {
                        return true;
                } else {
                        return false;
                }
        }

        /**
         * ユーザーID存在チェック（自身以外）
         * 
         * @param userId ユーザーID
         * @param userNo ユーザー番号
         * @return 自身以外で入力されたユーザーIDがある場合はtrue、そうでない場合はfalse
         */
        public boolean existsByUserIdAndUserNo(String userId, String userNo) {
                if (usersRepository.existsByUserIdAndUserNo(userId, CommonConst.DEL_FLG_ACTIVE, userNo)) {
                        return true;
                } else {
                        return false;
                }
        }

        /**
         * メールアドレス存在チェック（自身以外）
         * 
         * @param mailAddress メールアドレス
         * @param userNo      ユーザー番号
         * @return 自身以外で入力されたメールアドレスがある場合はtrue、そうでない場合はfalse
         */
        public boolean existsByMailAddressAndUserNo(String mailAddress, String userNo) {
                if (usersRepository.existsByMailAddressAndUserNo(mailAddress, CommonConst.DEL_FLG_ACTIVE,
                                userNo)) {
                        return true;
                } else {
                        return false;
                }
        }

        /**
         * ユーザー情報更新画面で表示するためにユーザーのデータを取得
         * 
         * @return ユーザー情報更新用DTO
         */
        public UsersUpdateDto getUserByUserNo() {
                // ユーザーデータ取得
                Users user = usersRepository
                                .findByUserNo(currentUserProvider.getLoginUserNo(), CommonConst.DEL_FLG_ACTIVE)
                                .orElseThrow(() -> new IllegalArgumentException("ユーザーテーブルの値が存在しません"));
                // ユーザー情報更新用DTOのインスタンス作成
                UsersUpdateDto dto = new UsersUpdateDto();
                // ユーザー情報更新画面で表示できるように、取得したデータをユーザー情報更新用DTOへセットしてDTOをreturn
                dto.setUserNo(user.getUserNo());
                dto.setUserId(user.getUserId());
                dto.setMailAddress(user.getMailAddress());
                return dto;
        }

        /**
         * ユーザー情報更新処理
         * 
         * @param dto ユーザー情報更新用DTO
         */
        @Transactional
        public void updateUser(UsersUpdateDto dto) {
                try {
                        // ユーザー情報の更新日時を登録するために現在日時を取得
                        LocalDateTime now = LocalDateTime.now();
                        // ユーザー情報更新用DTOを用いてユーザーの入力値を登録
                        usersRepository.updateUser(
                                        currentUserProvider.getLoginUserNo(),
                                        dto.getUserId(),
                                        dto.getMailAddress(),
                                        now);

                        // パスワードだけは変更しない場合は入力しないように画面上で記載しているためパスワードは入力された場合に専用のメソッドを使用
                        if (!(dto.getPassword() == null) && !(dto.getPassword().isBlank())) {
                                usersRepository.updatePassword(currentUserProvider.getLoginUserNo(),
                                                passwordEncoder.encode(dto.getPassword()));
                        }

                        // 画面などに表示するログインユーザー情報を更新するためPrincipalを再作成
                        loginSessionService.refreshLoginUser(currentUserProvider.getLoginUserNo());

                        // ログ出力
                        logger.info("ユーザー情報変更成功 userNo={}", currentUserProvider.getLoginUserNo());
                } catch (Exception e) {
                        // 失敗した場合はログ出力・exceptionをthrow
                        logger.error("ユーザー情報変更失敗", e);
                        throw new RuntimeException("ユーザー登録処理でエラーが発生しました", e);
                }
        }

        /**
         * ユーザー削除処理
         * 
         * @param userNo ユーザー番号
         */
        @Transactional
        public void deleteUser(String userNo) {
                try {
                        // ユーザー情報の更新日時を登録するために現在日時を取得
                        LocalDateTime now = LocalDateTime.now();
                        // 引数のユーザー番号を用いてユーザー削除
                        usersRepository.deleteUser(userNo, CommonConst.DEL_FLG_DELETED, now);
                        // ログ出力
                        logger.info("ユーザー削除成功 userNo={}", userNo);
                } catch (Exception e) {
                        // 失敗した場合はログ出力・exceptionをthrow
                        logger.error("ユーザー削除失敗", e);
                        throw new RuntimeException("ユーザー削除処理でエラーが発生しました", e);
                }
        }

}
