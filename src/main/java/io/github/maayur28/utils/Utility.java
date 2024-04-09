package io.github.maayur28.utils;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;

import java.util.UUID;

import static io.github.maayur28.utils.Constants.CONST_REQUEST_ID;
import static io.github.maayur28.utils.Constants.HEADERS_TO_TRY;

public final class Utility {

    private Utility() {
    }

    public static String getRequestId(HttpServletRequest request) {
        return StringUtils.isBlank(request.getHeader(CONST_REQUEST_ID)) ?
                UUID.randomUUID().toString() : request.getHeader(CONST_REQUEST_ID);
    }

    public static String getIPAddress(HttpServletRequest request) {
        for (String header : HEADERS_TO_TRY) {
            String ipAddress = request.getHeader(header);
            if (ipAddress != null && !ipAddress.isEmpty() && !"unknown".equalsIgnoreCase(ipAddress)) {
                return ipAddress;
            }
        }
        return request.getRemoteAddr();
    }
}
