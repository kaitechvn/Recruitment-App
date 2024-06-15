package com.example.recruitment.logging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Log4j2
public class LoggingServiceImpl implements LoggingService {

  @Autowired
  private RequestResponseRepository requestResponseRepository;

  @Autowired
  private ObjectMapper objectMapper;

  @Override
  public void logRequest(HttpServletRequest request, Object body) {
    RequestResponse reqres;

    String request_id = UUID.randomUUID().toString();

    reqres = RequestResponse.builder()
      .request_id(request_id)
      .requestAt(LocalDateTime.now())
      .build();

    request.setAttribute("request", reqres);

    reqres.setMethod(request.getMethod());
    reqres.setPath(request.getRequestURI());
    reqres.setParameters(buildRequestParameters(request));
    reqres.setRequestHeaders(buildRequestHeader(request));

    if (body != null) {
      try {
        reqres.setRequestBody(
          objectMapper.readValue(objectMapper.writeValueAsString(body), new TypeReference<>() {
          }));
      } catch (JsonProcessingException e) {
        log.warn("Could not parse request body: {}", body);
      }
    }
    requestResponseRepository.save(reqres);
    log.debug("REQUEST: {}", reqres);
  }

  @Override
  public void logResponse(HttpServletRequest request, HttpServletResponse response, Object body) {
      RequestResponse reqres = (RequestResponse) request.getAttribute("request");

      if (reqres == null) {
        reqres = createRequestResponseForDelete(request);
      }

      reqres.setResponseAt(LocalDateTime.now());
      reqres.setResponseHeaders(buildResponseHeaders(response));
      reqres.setStatusCode(response.getStatus());

      if (body != null) {
        try {
          reqres.setResponseBody(objectMapper.readValue(objectMapper.writeValueAsString(body), new TypeReference<>() {}));
        } catch (JsonProcessingException e) {
          log.warn("Could not parse response body: {}", body);
        }
      }

      requestResponseRepository.save(reqres);
      log.debug("RESPONSE: {}", reqres);
  }

  private RequestResponse createRequestResponseForDelete(HttpServletRequest request) {
    RequestResponse reqres = new RequestResponse();
    reqres.setMethod(request.getMethod());
    reqres.setPath(request.getRequestURI());
    reqres.setParameters(buildRequestParameters(request));
    reqres.setRequestHeaders(buildRequestHeader(request));
    return reqres;
  }

  private Map<String, String> buildRequestParameters(HttpServletRequest request) {
    Map<String, String> map = new HashMap<>();

    Enumeration<String> parameterNames = request.getParameterNames();
    while (parameterNames.hasMoreElements()) {
      String key = parameterNames.nextElement();
      String value = request.getParameter(key);
      map.put(key, value);
    }
    return map;
  }

  private Map<String, List<String>> buildRequestHeader(HttpServletRequest request) {
    Map<String, List<String>> map = new HashMap<>();

    Enumeration<String> headerNames = request.getHeaderNames();
    while (headerNames.hasMoreElements()) {
      String key = headerNames.nextElement();
      if (map.containsKey(key))
        continue;
      List<String> values = new ArrayList<>();
      Enumeration<String> headerValues = request.getHeaders(key);
      while (headerValues.hasMoreElements()) {
        values.add(headerValues.nextElement());
      }
      map.put(key, values);
    }
    return map;
  }

  private Map<String, List<String>> buildResponseHeaders(HttpServletResponse response) {
    Map<String, List<String>> map = new HashMap<>();

    Collection<String> headerNames = response.getHeaderNames();

    for (String header : headerNames) {
      if (map.containsKey(header))
        continue;
      List<String> values = new ArrayList<>();
      values.addAll(response.getHeaders(header));
      map.put(header, values);
    }
    return map;

  }
}
