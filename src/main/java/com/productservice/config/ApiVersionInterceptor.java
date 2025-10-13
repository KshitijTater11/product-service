package com.productservice.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class ApiVersionInterceptor implements HandlerInterceptor {

    private static final String REQUIRED_API_VERSION = "1.0";
    private static final String API_VERSION_HEADER = "X-API-Version";

    @Override
    public boolean preHandle(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler) throws Exception {

        String apiVersion = request.getHeader(API_VERSION_HEADER);

        // Check if the header is missing or empty
        if (apiVersion == null || apiVersion.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "Missing required header: " + API_VERSION_HEADER);
            return false;
        }

        // Check if the version is valid
        if (!apiVersion.equals(REQUIRED_API_VERSION)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "Unsupported API version: " + apiVersion + ". Only " + REQUIRED_API_VERSION + " is supported.");
            return false;
        }

        // If the header is correct, proceed with the request
        return true;
    }
}