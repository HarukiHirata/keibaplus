package com.keibaplus.webap.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.keibaplus.webap.dto.UsersResponseDto;
import com.keibaplus.webap.dto.UsersUpdateDto;
import com.keibaplus.webap.dto.UsersRegisterDto;
import com.keibaplus.webap.entity.Users;
import com.keibaplus.webap.entity.Saiban;
import com.keibaplus.webap.repository.UsersRepository;
import com.keibaplus.webap.repository.SaibanRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;

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
        private final SaibanRepository saibanRepository;
        private final PasswordEncoder passwordEncoder;
        // ロガーの定義
        private static final Logger logger = LoggerFactory.getLogger(UsersService.class);

        // コンストラクタ
        public UsersService(UsersRepository usersRepository, SaibanRepository saibanRepository,
                        PasswordEncoder passwordEncoder) {
                this.usersRepository = usersRepository;
                this.saibanRepository = saibanRepository;
                this.passwordEncoder = passwordEncoder;
        }

        /**
         * ログインユーザー情報取得処理
         * 
         * @return ログインユーザー情報
         */
        public LoginUser getLoginUser() {
                // Spring Securityの認証情報を取得
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                // ユーザー名以外のデータを取得するためにPrincipalを格納してそこからユーザー情報を取得
                LoginUser loginUser = (LoginUser) auth.getPrincipal();
                return loginUser;
        }

        /**
         * ログインユーザー番号取得処理
         * 
         * @return getLoginUser()のユーザー番号
         */
        public String getLoginUserNo() {
                return getLoginUser().getUserNo();
        }

        /**
         * ログインユーザーID取得処理
         * 
         * @return getLoginUser()のユーザーID
         */
        public String getLoginUserId() {
                return getLoginUser().getUserId();
        }

        /**
         * ログインユーザーメールアドレス取得処理
         * 
         * @return getLoginUser()のメールアドレス
         */
        public String getLoginUserMailAddress() {
                return getLoginUser().getMailAddress();
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
                        // ユーザーテーブルの収ユーザー番号を登録するために採番テーブルの値を取得して変数に格納
                        Saiban saiban = saibanRepository.findByTableName("USERS")
                                        .orElseThrow(() -> new IllegalArgumentException("採番テーブルの値が存在しません"));
                        String newUserNo = saiban.getPrefix() + saiban.getSaibanNo();
                        // ユーザーエンティティを用いてユーザーの入力値を登録
                        Users user = new Users(
                                        newUserNo,
                                        dto.getUserId(),
                                        passwordEncoder.encode(dto.getPassword()),
                                        dto.getMailAddress(),
                                        "0",
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

                        // 次のユーザーの登録に使用するため採番テーブルの値をインクリメント
                        String newSaibanNo = String.format("%08d", (Integer.parseInt(saiban.getSaibanNo()) + 1));

                        saibanRepository.updateSaibanNo(newSaibanNo, "USERS");

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
                if (usersRepository.existsByUserId(userId)) {
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
                if (usersRepository.existsByMailAddress(mailAddress)) {
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
                if (usersRepository.existsByUserIdAndUserNo(userId, userNo)) {
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
                if (usersRepository.existsByMailAddressAndUserNo(mailAddress, userNo)) {
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
                Users user = usersRepository.findByUserNo(getLoginUserNo())
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
                                        getLoginUserNo(),
                                        dto.getUserId(),
                                        dto.getMailAddress(),
                                        now);

                        // パスワードだけは変更しない場合は入力しないように画面上で記載しているためパスワードは入力された場合に専用のメソッドを使用
                        if (!(dto.getPassword().isBlank()) && !(dto.getPassword() == null)) {
                                usersRepository.updatePassword(getLoginUserNo(),
                                                passwordEncoder.encode(dto.getPassword()));
                        }

                        // ログ出力
                        logger.info("ユーザー情報変更成功 userNo={}", getLoginUserNo());
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
                        usersRepository.deleteUser(userNo, "1", now);
                        // ログ出力
                        logger.info("ユーザー削除成功 userNo={}", userNo);
                } catch (Exception e) {
                        // 失敗した場合はログ出力・exceptionをthrow
                        logger.error("ユーザー削除失敗", e);
                        throw new RuntimeException("ユーザー削除処理でエラーが発生しました", e);
                }
        }

}
