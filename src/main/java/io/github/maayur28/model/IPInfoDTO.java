package io.github.maayur28.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IPInfoDTO {

    private String ipAddress;

    private String userAgent;

    private String url;

    private String requestId;
}
