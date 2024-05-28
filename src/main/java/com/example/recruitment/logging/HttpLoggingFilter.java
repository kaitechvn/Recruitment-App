package com.example.recruitment.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.Enumeration;

@Slf4j
@Component
public class HttpLoggingFilter extends OncePerRequestFilter {
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
    ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

    filterChain.doFilter(requestWrapper, responseWrapper);
    logResponse(requestWrapper, responseWrapper);
  }

  private void logResponse(ContentCachingRequestWrapper requestWrapper, ContentCachingResponseWrapper responseWrapper) throws IOException {
    String requestBody = new String(requestWrapper.getContentAsByteArray());

    StringBuilder requestMessage = new StringBuilder();
    requestMessage.append("HTTP Method: ").append(requestWrapper.getMethod()).append("\n");
    requestMessage.append("Request URI: ").append(requestWrapper.getRequestURI());

    // Append query parameters, if any
    String queryString = requestWrapper.getQueryString();
    if (queryString != null) {
      requestMessage.append("?").append(queryString);
    }

    // Append request headers
    requestMessage.append("\nRequest Headers:");
    Enumeration<String> headerNames = requestWrapper.getHeaderNames();
    while (headerNames.hasMoreElements()) {
      String headerName = headerNames.nextElement();
      String headerValue = requestWrapper.getHeader(headerName);
      requestMessage.append("\n\t").append(headerName).append(": ").append(headerValue);
    }

    // Append request body, if not empty
    if (!requestBody.isEmpty()) {
      requestMessage.append("\nRequest Body: ").append(requestBody);
    }

    // Log the complete request details
    log.info(requestMessage.toString());

    log.info("Response Status Code: {}", responseWrapper.getStatus());
    log.info("Response {}", new String(responseWrapper.getContentAsByteArray()));
    responseWrapper.copyBodyToResponse();

  }
}
