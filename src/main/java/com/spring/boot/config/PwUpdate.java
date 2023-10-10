package com.spring.boot.config;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class PwUpdate {

    @NotEmpty(message = "새 비밀번호는 필수입니다.")
    @Size(min = 6, max = 20, message = "비밀번호는 6자 이상 20자 이하여야 합니다.")
    private String newPassword;

    // Getter와 Setter 메서드

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}