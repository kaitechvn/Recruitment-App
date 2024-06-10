package com.example.recruitment;

import com.example.recruitment.api.dto.in.AuthLoginDtoIn;
import com.example.recruitment.api.entity.User;
import com.example.recruitment.api.repository.UserRepository;
import com.example.recruitment.sample.Sample;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private UserRepository userRepository;

  private User setUpUser;

  Faker faker = new Faker();
  private final String password  = faker.internet().password();

  @Before
  public void setUp() {
    setUpUser = this.userRepository.save(Sample.generateSampleActiveUser(password));
  }

  @After
  public void tearDown() {
    this.userRepository.delete(setUpUser);
  }

  @Test
  public void testLoginSuccess() throws Exception {
    AuthLoginDtoIn loginDtoIn = new AuthLoginDtoIn();
    loginDtoIn.setUsername(setUpUser.getUsername());
    loginDtoIn.setPassword(password);

    mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(loginDtoIn)))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.message").value("Login successfully"))
      .andExpect(jsonPath("$.accessToken").exists());
  }

  @Test
  public void testLoginUsernameNotFound() throws Exception {
    AuthLoginDtoIn loginDtoIn = new AuthLoginDtoIn();
    loginDtoIn.setUsername(setUpUser.getUsername() + "notfound");
    loginDtoIn.setPassword(password);

    mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(loginDtoIn)))
      .andExpect(status().isNotFound())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.message").value("username not found"))
      .andExpect(jsonPath("$.accessToken").doesNotExist());
  }

  @Test
  @WithMockUser(username = "admin", roles = {"ADMIN"})
  public void testAccessWithAdminRole() throws Exception {
   mockMvc.perform(MockMvcRequestBuilders.get("/admin")
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(username = "user", roles = {"USER"})
  public void testAccessFailedWithUserRole() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/admin")
        .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isForbidden())
      .andExpect(jsonPath("$.errorCode").value(403))
      .andExpect(jsonPath("$.statusCode").value(403))
      .andExpect(jsonPath("$.message").value("Don't have permission to access this resource"));
  }

  @Test
  public void testAccessWithoutToken() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/admin")
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isUnauthorized())
      .andExpect(jsonPath("$.errorCode").value(401))
      .andExpect(jsonPath("$.statusCode").value(401))
      .andExpect(jsonPath("$.message").value("Unauthorized to access this resource"));
  }


}
