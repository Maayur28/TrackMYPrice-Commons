package io.github.maayur28.request;

import io.github.maayur28.model.IPInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRequest {

    private String email;

    private String password;

    private boolean rememberMe;

    private IPInfoDTO loginInfo;
}
