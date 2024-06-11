package com.example.recruitment;

import com.example.recruitment.api.dto.in.JobDtoIn;
import com.example.recruitment.api.dto.in.page.PageJobDtoIn;
import com.example.recruitment.api.entity.Employer;
import com.example.recruitment.api.entity.Job;
import com.example.recruitment.api.repository.EmployerRepository;
import com.example.recruitment.api.repository.JobRepository;
import com.example.recruitment.sample.Sample;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class JobTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private JobRepository jobRepository;

  @Autowired
  private EmployerRepository employerRepository;

  private Employer setUpEmployer;

  private Job testJob;

  @Before
  public void setUp() {
    setUpEmployer = this.employerRepository.save(Sample.generateEmployer());
    testJob = this.jobRepository.save(Sample.generateJob());

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
    this.jobRepository.delete(testJob);
    this.employerRepository.delete(setUpEmployer);
  }


  @Test
  public void testGetSuccess() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/job/{id}", testJob.getId()))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.errorCode").value(200))
      .andExpect(jsonPath("$.statusCode").value(200))
      .andExpect(jsonPath("$.object").exists())
      .andExpect(jsonPath("$.object.id").value(testJob.getId()));
  }

  @Test
  public void testAddSuccess() throws Exception {
    JobDtoIn jobDtoIn = Sample.generateJobDto();
    jobDtoIn.setEmployerId(setUpEmployer.getId());

    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/job")
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(jobDtoIn)))
      .andExpect(status().isCreated())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.errorCode").value(201))
      .andExpect(jsonPath("$.statusCode").value(201))
      .andExpect(jsonPath("$.object").exists())
      .andExpect(jsonPath("$.object.id").isNotEmpty())
      .andReturn();

    // Extract the job ID created during the test
    String responseBody = result.getResponse().getContentAsString();
    Integer createdJobId = new ObjectMapper().readTree(responseBody).path("object").path("id").asInt();

    // Clean up the job created during the test
    jobRepository.deleteById(createdJobId);
  }

  @Test
  public void testUpdateSuccess() throws Exception {
    JobDtoIn updatingJob = Sample.generateJobDto();
    updatingJob.setTitle("Updated Title");
    updatingJob.setEmployerId(setUpEmployer.getId());

    mockMvc.perform(MockMvcRequestBuilders.put("/job/{id}", testJob.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(updatingJob)))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.errorCode").value(200))
      .andExpect(jsonPath("$.statusCode").value(200))
      .andExpect(jsonPath("$.object").exists())
      .andExpect(jsonPath("$.object.id").value(testJob.getId()))
      .andExpect(jsonPath("$.object.title").value("Updated Title"));
  }

  @Test
  public void testUpdateFailIdNotExist() throws Exception {
    JobDtoIn updatingJob = Sample.generateJobDto();
    updatingJob.setEmployerId(setUpEmployer.getId());

    mockMvc.perform(MockMvcRequestBuilders.put("/job/{id}", Sample.generateRandomId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(updatingJob)))
      .andExpect(status().isNotFound())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.errorCode").value(404))
      .andExpect(jsonPath("$.statusCode").value(404))
      .andExpect(jsonPath("$.message").value("job not found"));
  }

  @Test
  public void testDeleteSuccess() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.delete("/job/{id}", testJob.getId()))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.errorCode").value(200))
      .andExpect(jsonPath("$.statusCode").value(200))
      .andExpect(jsonPath("$.object").exists());
  }

  @Test
  public void testListSuccess() throws Exception {
    PageJobDtoIn pageJobDtoIn = Sample.generateJobPageDto();

    mockMvc.perform(MockMvcRequestBuilders.get("/job")
        .param("page", String.valueOf(pageJobDtoIn.getPage()))
        .param("pageSize", String.valueOf(pageJobDtoIn.getPageSize()))
        .param("employerId", String.valueOf(pageJobDtoIn.getEmployerId())))
      .andExpect(status().isOk())
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(jsonPath("$.errorCode").value(200))
      .andExpect(jsonPath("$.statusCode").value(200))
      .andExpect(jsonPath("$.object").exists())
      .andExpect(jsonPath("$.object.data").exists());
  }
}
