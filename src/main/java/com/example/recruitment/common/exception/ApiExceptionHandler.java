package com.example.recruitment.common.exception;

import com.example.recruitment.common.dto.CommonDtoOut;
import com.example.recruitment.common.code.ErrorCode;
import com.example.recruitment.sentry.SentryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeType;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Collectors;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
  @Autowired
  private SentryException sentryException;

  private static final Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);

  @ExceptionHandler(value = ApiException.class)
  public ResponseEntity<?> handleApiException(ApiException e) {
    sentryException.capture(e, e.getHttpStatus());
    return responseEntity(e.getErrorCode(), e.getHttpStatus(), e.getMessage());
  }

  private ResponseEntity<Object> responseEntity(Integer errorCode, HttpStatusCode statusCode, String msg) {
    return new ResponseEntity<>(
      CommonDtoOut.builder()
        .errorCode(errorCode)
        .statusCode(statusCode.value())
        .message(msg)
        .build(),
      statusCode);
  }

  @Override
  protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    String supportedMethods = ex.getSupportedMethods() == null ? null : String.join(",", ex.getSupportedMethods());
    String msg = String.format("Method not supported: %s, support %s", ex.getMethod(), supportedMethods);
    logger.debug("Request error: Method not supported");
    sentryException.capture(ex, status);
    return responseEntity(ErrorCode.METHOD_NOT_ALLOWED, status, msg);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
                                                                   HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    String supportedContentTypes = ex.getSupportedMediaTypes().stream().map(MimeType::toString)
      .collect(Collectors.joining(", ")); // MimeType for MediaType Object

    String msg = String.format("MediaType not supported: %s, only support %s", ex.getContentType(),
      supportedContentTypes);
    logger.debug("Request error: MediaType not supported");
    sentryException.capture(ex, status);
    return responseEntity(ErrorCode.UNSUPPORTED_MEDIA_TYPE, status, msg);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
                                                                    HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    String supportedContentTypes = ex.getSupportedMediaTypes().stream().map(MimeType::toString)
      .collect(Collectors.joining(", "));

    String acceptHeader = request.getHeader("Accept");

    String msg = String.format("MediaType Client not acceptable: %s, only accept %s", supportedContentTypes, acceptHeader);
    logger.debug("4xx Error");
    sentryException.capture(ex, status);
    return responseEntity(ErrorCode.NOT_ACCEPTABLE, status, msg);
  }

  @Override
  protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers,
                                                             HttpStatusCode status, WebRequest request) {
    String msg = String.format("MissingPathVariable: variable name %s", ex.getVariableName());
    logger.error("5xx Error");
    sentryException.capture(ex, status);
    return responseEntity(ErrorCode.INTERNAL_ERROR, status, msg);
  }

  @Override
  protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
                                                                 HttpStatusCode status, WebRequest request) {
    String msg = String.format("NoHandlerFound: method %s, url %s", ex.getHttpMethod(), ex.getRequestURL());
    logger.debug("4xx Error");
    sentryException.capture(ex, status);
    return responseEntity(ErrorCode.NOT_FOUND, status, msg);
  }

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                        HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    String msg = String.format("MissingServletRequestParameter: parameter name %s", ex.getParameterName());
    logger.debug("4xx Error");
    sentryException.capture(ex, status);
    return responseEntity(ErrorCode.BAD_REQUEST, status, msg);
  }

  @Override
  protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex,
                                                                        HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    String msg = String.format("ServletRequestBinding: detail message code %s", ex.getDetailMessageCode());
    logger.debug("4xx Error");
    sentryException.capture(ex, status);
    return responseEntity(ErrorCode.BAD_REQUEST, status, msg);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                HttpHeaders headers, HttpStatusCode status, WebRequest request) {
    String fieldErrors = ex.getFieldErrors().stream()
      .map(fieldError -> String.format("%s:%s", fieldError.getField(), fieldError.getDefaultMessage()))
      .collect(Collectors.joining(","));

    String glObjectErrors = ex.getGlobalErrors().stream().map(ObjectError::getObjectName)
      .collect(Collectors.joining(","));

    String msg = String.format("MethodArgumentNotValid field errors: %s, global errors: %s", fieldErrors,
      glObjectErrors);
    logger.debug("4xx Error");
    sentryException.capture(ex, status);
    return responseEntity(ErrorCode.BAD_REQUEST, status, msg);
  }

}
