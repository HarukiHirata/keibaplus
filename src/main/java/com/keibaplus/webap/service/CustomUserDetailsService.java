package com.keibaplus.webap.service;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.keibaplus.webap.common.CommonConst;
import com.keibaplus.webap.entity.Users;
import com.keibaplus.webap.repository.UsersRepository;

/**
 * ログインユーザーのデータを取得するためのUserDetailsServiceを独自で実装したクラス
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
    // ユーザーデータを取得するためUsersRepositoryのインスタンスを使用
    private final UsersRepository usersRepository;

    // コンストラクタ
    public CustomUserDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    /**
     * 収支登録でユーザー番号を設定したりトップ画面でユーザーIDを表示したりするためにログインユーザーの情報を取得
     * 
     * @param username ユーザーID
     */
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        // ユーザーIDで検索したデータをusersエンティティに格納
        Users user = usersRepository.findByUserId(username, CommonConst.DEL_FLG_ACTIVE)
                .orElseThrow(() -> new UsernameNotFoundException("ユーザーが存在しません"));

        // // 取得したデータを使用できるようにするためUserDetailsを独自で実装したLoginUserのインスタンスに格納
        // return new LoginUser(user.getUserNo(),
        // user.getUserId(),
        // user.getMailAddress(),
        // user.getPassword(),
        // true,
        // true,
        // true,
        // true,
        // AuthorityUtils.createAuthorityList("ROLE_USER"));
        return createLoginUser(user);
    }

    public UserDetails loadUserByUserNo(String userNo)
            throws UsernameNotFoundException {
        // ユーザーIDで検索したデータをusersエンティティに格納
        Users user = usersRepository.findByUserNo(userNo, CommonConst.DEL_FLG_ACTIVE)
                .orElseThrow(() -> new UsernameNotFoundException("ユーザーが存在しません"));

        return createLoginUser(user);
    }

    private LoginUser createLoginUser(Users user) {
        return new LoginUser(
                user.getUserNo(),
                user.getUserId(),
                user.getMailAddress(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                AuthorityUtils.createAuthorityList("ROLE_USER"));
    }
}
