package com.spring.boot.util;

import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class PasswordGenerator {
	// 랜덤 비밀번호 길이
    private static final int PASSWORD_LENGTH = 12;

    // 랜덤 비밀번호 생성에 사용할 문자열
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";

    public static String generateRandomPassword() {
        Random random = new Random();
        StringBuilder password = new StringBuilder();

        // 비밀번호 길이만큼 반복하여 랜덤 문자 선택
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            password.append(randomChar);
        }

        return password.toString();
    }
}
