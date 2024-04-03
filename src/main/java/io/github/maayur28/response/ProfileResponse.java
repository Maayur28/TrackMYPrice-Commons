package io.github.maayur28.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponse {

    private String firstName;

    private String lastName;

    private String email;

    private String dateOfBirth;

    private String gender;

    private String country;

    private String profilePictureUrl;

    private String profileCoverUrl;
}
