package io.github.maayur28.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileRequest {

    private String firstName;

    private String lastName;

    private String email;

    private String dateOfBirth;

    private String gender;

    private String country;

    private String profilePictureUrl;

    private String profileCoverUrl;
}
