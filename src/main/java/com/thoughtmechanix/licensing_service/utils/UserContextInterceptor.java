package com.thoughtmechanix.licensing_service.utils;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserContextInterceptor implements RequestInterceptor {
    
    @Override
    public void apply(RequestTemplate requestTemplate) {
        addHeaderIfPresent(requestTemplate, UserContext.CORRELATION_ID, UserContext.getCorrelationId());
        addHeaderIfPresent(requestTemplate, UserContext.AUTH_TOKEN, UserContext.getAuthToken());
    }

    private void addHeaderIfPresent(RequestTemplate requestTemplate, String headerName, String headerValue) {
        if (headerValue != null && !headerValue.isBlank()) {
            requestTemplate.header(headerName, headerValue);
        }
    }
}
