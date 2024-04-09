package io.github.maayur28.interceptor;

import io.github.maayur28.utils.Utility;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static io.github.maayur28.utils.Constants.CONST_REQUEST_ID;
import static io.github.maayur28.utils.Constants.IP_ADDRESS;

@WebFilter("/*")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RequestLoggingFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(@SuppressWarnings("NullableProblems") HttpServletRequest request, @SuppressWarnings("NullableProblems") HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String requestId = Utility.getRequestId(request);
        String ipAddress = Utility.getIPAddress(request);

        try {
            MDC.put(CONST_REQUEST_ID, requestId);
            MDC.put(IP_ADDRESS, ipAddress);
            filterChain.doFilter(request, response);
        } finally {
            MDC.remove(CONST_REQUEST_ID);
            MDC.remove(IP_ADDRESS);
        }
    }
}

