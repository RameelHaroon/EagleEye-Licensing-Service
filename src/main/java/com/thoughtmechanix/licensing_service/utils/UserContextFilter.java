package com.thoughtmechanix.licensing_service.utils;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UserContextFilter implements Filter {

    private static final Logger logger =
            LoggerFactory.getLogger(UserContextFilter.class);
    private static final String CORRELATION_ID_LOG_KEY = "correlationId";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        try {
            UserContext.setCorrelationId(httpRequest.getHeader(UserContext.CORRELATION_ID));
            UserContext.setUserId(httpRequest.getHeader(UserContext.USER_ID));
            UserContext.setAuthToken(httpRequest.getHeader(UserContext.AUTH_TOKEN));
            UserContext.setOrgId(httpRequest.getHeader(UserContext.ORG_ID));

            if (hasText(UserContext.getCorrelationId())) {
                MDC.put(CORRELATION_ID_LOG_KEY, UserContext.getCorrelationId());
            }

            logger.debug("UserContextFilter - tmx-correlation-id: {}", UserContext.getCorrelationId());
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            MDC.remove(CORRELATION_ID_LOG_KEY);
            UserContext.clear();
        }
    }

    @Override
    public void destroy() {
    }

    private boolean hasText(String value) {
        return value != null && !value.isBlank();
    }
}
