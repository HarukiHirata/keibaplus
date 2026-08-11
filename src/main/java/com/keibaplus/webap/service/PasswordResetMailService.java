package com.keibaplus.webap.service;

public interface PasswordResetMailService {

    void sendResetLink(String mailAddress, String rawToken);
}
