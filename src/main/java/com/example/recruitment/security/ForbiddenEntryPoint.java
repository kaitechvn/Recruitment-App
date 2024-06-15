package com.example.recruitment.security;

import com.example.recruitment.common.dto.CommonDtoOut;
import com.example.recruitment.common.code.ErrorCode;
import com.example.recruitment.sentry.SentryException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.sentry.Sentry;
import io.sentry.SentryEvent;
import io.sentry.SentryLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("customForbiddenEntryPoint")
public class ForbiddenEntryPoint implements AccessDeniedHandler {
  @Autowired
  private SentryException sentryException;

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
                     AccessDeniedException accessDeniedException) throws IOException, ServletException {

    CommonDtoOut res = CommonDtoOut.builder()
      .errorCode(ErrorCode.FORBIDDEN)
      .statusCode(HttpStatus.FORBIDDEN.value())
      .message("Don't have permission to access this resource")
      .build();

    ObjectMapper objectMapper = new ObjectMapper();
    String jsonResponse = objectMapper.writeValueAsString(res);

    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(HttpStatus.FORBIDDEN.value());

    response.getWriter().write(jsonResponse);
    sentryException.capture(accessDeniedException, HttpStatus.FORBIDDEN);
  }
}




