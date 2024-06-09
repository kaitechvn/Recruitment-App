package com.example.recruitment.security;

import com.example.recruitment.common.dto.CommonDtoOut;
import com.example.recruitment.common.code.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component("customUnauthorizedEntryPoint")
public class UnauthorizedEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
    throws IOException, ServletException {

    CommonDtoOut res =  CommonDtoOut.builder()
      .errorCode(ErrorCode.UNAUTHORIZED)
      .statusCode(HttpStatus.UNAUTHORIZED.value())
      .message("Unauthorized to access this resource")
      .build();

    // Convert the object to JSON
    ObjectMapper objectMapper = new ObjectMapper();
    String jsonResponse = objectMapper.writeValueAsString(res);

    // Set response headers
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(HttpStatus.UNAUTHORIZED.value());

    // Write the JSON response to the output stream
    response.getWriter().write(jsonResponse);
  }


}
