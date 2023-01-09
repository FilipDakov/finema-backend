package com.fd.finema.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassResetRequest extends AuthRequest{
    private String passResetToken;

    public String getPassResetToken() {
        return passResetToken;
    }

    public void setPassResetToken(String passResetToken) {
        this.passResetToken = passResetToken;
    }
}
