package com.rabin.backend.security;

import com.rabin.backend.dto.response.GenericApiResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        GenericApiResponse<Object> genericApiResponse = new GenericApiResponse<>(false,
                "Authorization required",
                null,
                HttpServletResponse.SC_UNAUTHORIZED // 401
        );

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getOutputStream().println(new ObjectMapper().writeValueAsString(genericApiResponse));
    }
}
