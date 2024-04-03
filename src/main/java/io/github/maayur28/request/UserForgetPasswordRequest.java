package io.github.maayur28.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserForgetPasswordRequest {

    private String email;

    private String ipAddress;
}
