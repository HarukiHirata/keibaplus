package com.keibaplus.webap.repository;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

import com.keibaplus.webap.entity.Users;

/**
 * ユーザーテーブル用リポジトリ
 */
public interface UsersRepository extends ListCrudRepository<Users, String> {

        /**
         * ユーザー情報更新画面でデータを表示するためユーザー番号で検索する
         * 
         * @param userNo ユーザー番号
         * @return ユーザーテーブル取得結果
         */
        @Query("SELECT * FROM USERS WHERE USER_NO = :userNo")
        Optional<Users> findByUserNo(@Param("userNo") String userNo);

        /**
         * spring security関連で使用するためユーザーIDで検索する
         * 
         * @param userId ユーザーID
         * @return ユーザーテーブル取得結果
         */
        @Query("SELECT * FROM USERS WHERE USER_ID = :userId")
        Optional<Users> findByUserId(@Param("userId") String userId);

        /**
         * 重複するユーザーIDがないかを確認するために該当のユーザーIDの有無を検索
         * 
         * @param userId ユーザーID
         * @return 検索対象のユーザーIDの有無（1件以上存在する場合は「1」そうでない場合は「0」を返却）
         */
        @Query("""
                        SELECT CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END
                        FROM USERS
                        WHERE USER_ID = :userId
                        AND DEL_FLG = '0'
                        """)
        boolean existsByUserId(String userId);

        /**
         * 重複するメールアドレスがないかを確認するために該当のメールアドレスの有無を検索
         * 
         * @param mailAddress メールアドレス
         * @return 検索対象のメールアドレスの有無（1件以上存在する場合は「1」そうでない場合は「0」を返却）
         */
        @Query("""
                        SELECT CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END
                        FROM USERS
                        WHERE MAIL_ADDRESS = :mailAddress
                        AND DEL_FLG = '0'
                        """)

        boolean existsByMailAddress(String mailAddress);

        /**
         * 自身以外で重複するユーザーIDがないかを確認するために該当のユーザーIDの有無を検索
         * 
         * @param userId ユーザーID
         * @param userNo ユーザー番号
         * @return 検索対象のユーザーIDの有無（1件以上存在する場合は「1」そうでない場合は「0」を返却）
         */
        @Query("""
                        SELECT CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END
                        FROM USERS
                        WHERE USER_ID = :userId
                        AND DEL_FLG = '0'
                        AND USER_NO != :userNo
                        """)
        boolean existsByUserIdAndUserNo(String userId, String userNo);

        /**
         * 自身以外で重複するメールアドレスがないかを確認するために該当のメールアドレスの有無を検索
         * 
         * @param mailAddress メールアドレス
         * @param userNo      ユーザー番号
         * @return 検索対象のメールアドレスの有無（1件以上存在する場合は「1」そうでない場合は「0」を返却）
         */
        @Query("""
                        SELECT CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END
                        FROM USERS
                        WHERE MAIL_ADDRESS = :mailAddress
                        AND DEL_FLG = '0'
                        AND USER_NO != :userNo
                        """)

        boolean existsByMailAddressAndUserNo(String mailAddress, String userNo);

        /**
         * ユーザー登録
         * 
         * @param userNo      ユーザー番号
         * @param userId      ユーザーID
         * @param password    パスワード
         * @param mailAddress メールアドレス
         * @param delFlg      削除フラグ
         * @param insDate     登録日時
         * @param updDate     更新日時
         */
        @Modifying
        @Query("""
                            INSERT INTO USERS
                            (USER_NO, USER_ID, PASSWORD, MAIL_ADDRESS, DEL_FLG, INS_DATE, UPD_DATE)
                            VALUES
                            (:userNo, :userId, :password, :mailAddress, :delFlg, :insDate, :updDate)
                        """)
        void registerUser(@Param("userNo") String userNo,
                        @Param("userId") String userId,
                        @Param("password") String password,
                        @Param("mailAddress") String mailAddress,
                        @Param("delFlg") String delFlg,
                        @Param("insDate") LocalDateTime insDate,
                        @Param("updDate") LocalDateTime updDate);

        /**
         * ユーザー情報更新
         * 
         * @param userNo      ユーザー番号
         * @param userId      ユーザーID
         * @param mailAddress メールアドレス
         * @param updDate     更新日時
         */
        @Modifying
        @Query("""
                        UPDATE USERS
                        SET USER_ID = :userId,
                        MAIL_ADDRESS = :mailAddress,
                        UPD_DATE = :updDate
                        WHERE USER_NO = :userNo
                        """)
        void updateUser(@Param("userNo") String userNo,
                        @Param("userId") String userId,
                        @Param("mailAddress") String mailAddress,
                        @Param("updDate") LocalDateTime updDate);

        /**
         * パスワード更新（ユーザー情報更新画面でパスワード更新しない場合は未入力としているためほかの項目とメソッドを分割）
         * 
         * @param userNo   ユーザー番号
         * @param password パスワード
         */
        @Modifying
        @Query("""
                        UPDATE USERS
                        SET PASSWORD = :password
                        WHERE USER_NO = :userNo
                        """)
        void updatePassword(@Param("userNo") String userNo,
                        @Param("password") String password);

        /**
         * ユーザー削除
         * 
         * @param userNo  ユーザー番号
         * @param delFlg  削除フラグ
         * @param updDate 更新日時
         */
        @Modifying
        @Query("""
                        UPDATE USERS
                        SET DEL_FLG = :delFlg,
                        UPD_DATE = :updDate
                        WHERE USER_NO = :userNo
                        """)
        void deleteUser(@Param("userNo") String userNo,
                        @Param("delFlg") String delFlg,
                        @Param("updDate") LocalDateTime updDate);

}
