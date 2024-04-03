package io.github.maayur28.request;

import io.github.maayur28.model.IPInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSignUpRequest {

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String confirmPassword;

    private String profilePictureUrl;

    private String profileCoverUrl;

    private String dateOfBirth;

    private String gender;

    private String country;

    private IPInfoDTO registeredInfo;
}
