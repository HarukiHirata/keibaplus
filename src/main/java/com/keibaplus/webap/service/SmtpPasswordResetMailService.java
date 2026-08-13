package com.keibaplus.webap.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * パスワード再設定用メール送信処理関連のService
 */
@Service
public class SmtpPasswordResetMailService implements PasswordResetMailService {

    // 必要なインスタンスを設定
    private final JavaMailSender mailSender;
    private final String baseUrl;
    private final String fromAddress;

    // コンストラクタ
    public SmtpPasswordResetMailService(
            JavaMailSender mailSender,
            @Value("${app.password-reset.base-url}") String baseUrl,
            @Value("${app.mail.from}") String fromAddress) {
        this.mailSender = mailSender;
        this.baseUrl = baseUrl;
        this.fromAddress = fromAddress;
    }

    /**
     * @param mailAddress メールアドレス
     * @param rawToken    トークン
     */
    @Override
    public void sendResetLink(
            String mailAddress,
            String rawToken) {

        // 送信されたトークンをパスワード再設定画面のパスに付け加えてURLにする
        String resetUrl = UriComponentsBuilder
                .fromUriString(baseUrl)
                .pathSegment("password-reset")
                .queryParam("token", rawToken)
                .build()
                .encode()
                .toUriString();

        // メールの設定
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromAddress);
        message.setTo(mailAddress);
        message.setSubject("【keibaplus】パスワード再設定のご案内");
        message.setText(createMailBody(resetUrl));

        // メールの送信処理
        mailSender.send(message);
    }

    /**
     * メール本文をreturn
     * 
     * @param resetUrl パスワード再設定画面URL
     * @return メール本文
     */
    private String createMailBody(String resetUrl) {
        // パスワード再設定画面URLをメール本文に組み込んでreturn
        return """
                keibaplusをご利用いただきありがとうございます。

                パスワードの再設定が申請されました。
                以下のURLから新しいパスワードを設定してください。

                %s

                このURLの有効期限は30分です。
                URLが無効になってしまった場合はお手数ですが再度申請をお願いします。
                お心当たりがない場合は、このメールを破棄してください。

                ※このメールは送信専用です。
                """.formatted(resetUrl);
    }
}