package com.prodnees.dto.user;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SecPasswordDto {

    @NotBlank(message = "oldPassword cannot be null or blank")
    private String oldPassword;
    @NotBlank
    @Size(min = 6, message = "newPassword must be at least 6 characters long")
    private String newPassword;
    @Size(min = 6, message = "repeatNewPassword must be at least 6 characters long")
    private String repeatNewPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public SecPasswordDto setNewPassword(String newPassword) {
        this.newPassword = newPassword;
        return this;
    }

    public String getRepeatNewPassword() {
        return repeatNewPassword;
    }

    public SecPasswordDto setRepeatNewPassword(String repeatNewPassword) {
        this.repeatNewPassword = repeatNewPassword;
        return this;
    }
}
