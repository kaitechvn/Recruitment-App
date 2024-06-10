package com.example.recruitment;

import com.example.recruitment.common.dto.CommonDtoOut;
import com.example.recruitment.api.dto.in.EmployerDtoIn;
import com.example.recruitment.api.dto.in.UpdateEmployerDtoIn;
import com.example.recruitment.api.dto.in.page.PageEmployerDtoIn;
import com.example.recruitment.api.dto.out.pagedata.DataEmployer;
import com.example.recruitment.common.dto.PageDtoOut;
import com.example.recruitment.api.entity.Employer;
import com.example.recruitment.api.repository.EmployerRepository;
import com.example.recruitment.sample.Sample;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployerTest {

    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private TestRestTemplate template;

    @LocalServerPort
    int randomServerPort;

    private Employer testEmployer;
    @Before
    public void setUp() {
      testEmployer = this.employerRepository.save(Sample.generateEmployer());

      // Set up mock user
      UserDetails userDetails = User.withUsername("testuser")
        .password("password")
        .roles("USER")
        .build();

      SecurityContextHolder.getContext().setAuthentication(
        new UsernamePasswordAuthenticationToken(userDetails, "password", userDetails.getAuthorities()));
    }

    @After
    public void tearDown() {
      this.employerRepository.delete(testEmployer);
    }

    @Test
    public void testGetSuccess() throws Exception {
      final String endpoint ="http://localhost:"+randomServerPort+"/employer/" + testEmployer.getId().toString();
      URI uri = new URI(endpoint);

      ResponseEntity<String> result = this.template.getForEntity(uri, String.class);
      assertEquals(200, result.getStatusCodeValue());

    }

    @Test
    public void testAddSuccess() throws Exception {
        final String endpoint ="http://localhost:"+randomServerPort+"/employer";
        URI uri = new URI(endpoint);

        EmployerDtoIn reqBody = Sample.generateEmployerDto();

        HttpEntity<EmployerDtoIn> request = new HttpEntity<>(reqBody);
        ResponseEntity<String> result = this.template.postForEntity(uri, request, String.class);
        assertEquals(201, result.getStatusCodeValue());


         // Xoa object vua tao
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(result.getBody());
        JsonNode employerNode = root.get("object"); // Assuming "object" is the name of the object containing ID
        Integer createdEmployerId = employerNode.get("id").asInt();
        this.employerRepository.deleteById(createdEmployerId);
    }

    @Test
    public void testUpdateSuccess()  throws Exception {
      final String endpoint ="http://localhost:"+randomServerPort+"/employer/" + testEmployer.getId().toString();
      URI uri = new URI(endpoint);

      UpdateEmployerDtoIn reqBody = Sample.generateUpdateEmployerDto();
      HttpEntity<UpdateEmployerDtoIn> request = new HttpEntity<>(reqBody);
      ResponseEntity<String> result = this.template.exchange(uri, HttpMethod.PUT, request, String.class);

      assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    public void testIdNotExistUpdateError() throws Exception {
      String idUri = String.valueOf(Sample.generateRandomId());
      final String endpoint ="http://localhost:"+randomServerPort+"/employer/" + idUri;
      URI uri = new URI(endpoint);

      UpdateEmployerDtoIn reqBody = Sample.generateUpdateEmployerDto();
      HttpEntity<UpdateEmployerDtoIn> request = new HttpEntity<>(reqBody);
      ResponseEntity<String> result = this.template.exchange(uri, HttpMethod.PUT, request, String.class);

      assertEquals(404, result.getStatusCodeValue());
    }

    @Test
    public void testDeleteSuccess() throws Exception {
      final String endpoint ="http://localhost:"+randomServerPort+"/employer/" + testEmployer.getId().toString();
      URI uri = new URI(endpoint);

      ResponseEntity<String> result = this.template.exchange(uri, HttpMethod.DELETE, null, String.class);
      assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    public void testListSuccess() throws Exception {
      PageEmployerDtoIn testPageDto = Sample.generateEmployerPageDto();

      final String endpoint ="http://localhost:"+randomServerPort+"/employer";
      URI uri = UriComponentsBuilder.fromUriString(endpoint)
        .queryParam("page",testPageDto.getPage())
        .queryParam("pageSize", testPageDto.getPageSize())
        .build()
        .toUri();

      ResponseEntity<String> result = this.template.getForEntity(uri, String.class);
      assertEquals(200, result.getStatusCodeValue());
    }

  @Test
  public void testListResult() throws Exception {
  PageEmployerDtoIn testPageDto = Sample.generateEmployerPageDto();

  final String endpoint ="http://localhost:"+randomServerPort+"/employer";
  URI uri = UriComponentsBuilder.fromUriString(endpoint)
    .queryParam("page",testPageDto.getPage())
    .queryParam("pageSize", testPageDto.getPageSize())
    .build()
    .toUri();


    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

    // Making GET request with query parameters
    ResponseEntity<CommonDtoOut<PageDtoOut<DataEmployer>>> result = template.exchange(
      uri,
      HttpMethod.GET,
      requestEntity,
      new ParameterizedTypeReference<>() {
      });

    CommonDtoOut<PageDtoOut<DataEmployer>> body = result.getBody();
    long totalElement = body.getObject().getTotalElements();
    long realTotalPage = body.getObject().getTotalPages();

    long expectedTotalPage = totalElement / testPageDto.getPageSize();
    if ( totalElement % testPageDto.getPageSize() != 0) {
      expectedTotalPage = expectedTotalPage + 1;
    }
    if (realTotalPage == 0) {
      expectedTotalPage = expectedTotalPage -1;
    }

    assertEquals(expectedTotalPage, realTotalPage);
    }

  @Test
  public void testGetSuccessWithCache() throws Exception {
    final String endpoint ="http://localhost:"+randomServerPort+"/employer/" + testEmployer.getId().toString();
    URI uri = new URI(endpoint);

    // First call - should be slower as it involves fetching and caching
    long startTime1 = System.nanoTime();
    ResponseEntity<String> response1 = this.template.getForEntity(uri, String.class);
    long duration1 = System.nanoTime() - startTime1;

    // Second call - should be faster as it hits the cache
    long startTime2 = System.nanoTime();
    ResponseEntity<String> response2 = this.template.getForEntity(uri, String.class);
    long duration2 = System.nanoTime() - startTime2;

    // Assert that the responses are equal
    assertThat(response1.getBody()).isEqualTo(response2.getBody());

    // Assert that the second call is faster
    assertThat(duration2).isLessThan(duration1);
  }

  @Test
  public void testGetUpdateWithCachePut() throws Exception {
    final String endpoint ="http://localhost:"+randomServerPort+"/employer/" + testEmployer.getId().toString();
    URI uri = new URI(endpoint);

    ResponseEntity<String> response1 = this.template.getForEntity(uri, String.class);

    UpdateEmployerDtoIn reqBody = Sample.generateUpdateEmployerDto();
    HttpEntity<UpdateEmployerDtoIn> request = new HttpEntity<>(reqBody);
    ResponseEntity<String> response2 = this.template.exchange(uri, HttpMethod.PUT, request, String.class);

    ResponseEntity<String> response3 = this.template.getForEntity(uri, String.class);

    // After update the get need not to be equal
    // For detail, assert response3 body as updated changes
    assertNotEquals(response1.getBody(), response3.getBody(), "Response bodies should not be equal");
    assertEquals(response2.getBody(), response3.getBody(), "Response bodies should be equal");
  }

  @Test
  public void testGetDeleteWithCacheEvict() throws Exception {
    final String endpoint ="http://localhost:"+randomServerPort+"/employer/" + testEmployer.getId().toString();
    URI uri = new URI(endpoint);

    this.template.getForEntity(uri, String.class);

    this.template.exchange(uri, HttpMethod.DELETE, null, Void.class);

    ResponseEntity<String> response = this.template.getForEntity(uri, String.class);
    assertEquals(404, response.getStatusCodeValue(), "Request should return status code 404");


  }
}






