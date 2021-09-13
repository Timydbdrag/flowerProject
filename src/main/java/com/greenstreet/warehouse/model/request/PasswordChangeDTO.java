package com.greenstreet.warehouse.model.request;

import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
public class PasswordChangeDTO {

    @NotNull
    private String currentPassword;
    @NotNull
    private String newPassword;

    public PasswordChangeDTO(String currentPassword, String newPassword) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
