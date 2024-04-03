package io.github.maayur28.model.ipinfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IPInfoRequestModel {

    private String ipAddress;

    private String userAgent;

    private String url;

    private String requestId;

}
