package com.keibaplus.webap.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class SmtpPasswordResetMailService implements PasswordResetMailService {

    private final JavaMailSender mailSender;
    private final String baseUrl;
    private final String fromAddress;

    public SmtpPasswordResetMailService(
            JavaMailSender mailSender,
            @Value("${app.password-reset.base-url}") String baseUrl,
            @Value("${app.mail.from}") String fromAddress) {
        this.mailSender = mailSender;
        this.baseUrl = baseUrl;
        this.fromAddress = fromAddress;
    }

    @Override
    public void sendResetLink(
            String mailAddress,
            String rawToken) {

        String resetUrl = UriComponentsBuilder
                .fromUriString(baseUrl)
                .pathSegment("password-reset")
                .queryParam("token", rawToken)
                .build()
                .encode()
                .toUriString();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromAddress);
        message.setTo(mailAddress);
        message.setSubject("【keibaplus】パスワード再設定のご案内");
        message.setText(createMailBody(resetUrl));

        mailSender.send(message);
    }

    private String createMailBody(String resetUrl) {
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