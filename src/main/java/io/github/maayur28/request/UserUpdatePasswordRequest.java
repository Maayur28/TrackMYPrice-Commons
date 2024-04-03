package io.github.maayur28.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdatePasswordRequest {

    private String email;

    private String currentPassword;

    private String newPassword;

    private String confirmPassword;
}
