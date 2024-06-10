package com.example.recruitment.sample;

import com.example.recruitment.api.dto.in.EmployerDtoIn;
import com.example.recruitment.api.dto.in.JobDtoIn;
import com.example.recruitment.api.dto.in.UpdateEmployerDtoIn;
import com.example.recruitment.api.dto.in.page.PageEmployerDtoIn;
import com.example.recruitment.api.dto.in.page.PageJobDtoIn;
import com.example.recruitment.api.entity.Employer;
import com.example.recruitment.api.entity.Job;
import com.example.recruitment.api.entity.User;
import com.github.javafaker.Faker;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Sample {
  private static final Integer MAX_TOTAL_PAGE = 10;
  private static final Integer MAX_PAGE_SIZE = 499;

  public static Integer generateRandomId() {
    Random random = new Random();
    return random.nextInt() + 1;
  }

  public static Employer generateEmployer() {
    Faker faker = new Faker();

    Integer id = faker.number().numberBetween(1, 1000); // Random ID
    String email = faker.internet().emailAddress();
    String name = faker.company().name();
    Integer province = faker.number().numberBetween(1, 10); // Example province number
    String description = faker.lorem().sentence();
    Date createdAt = faker.date().past(365, TimeUnit.DAYS);
    Date updatedAt = faker.date().past(30, TimeUnit.DAYS); // Updated within the last month

    return new Employer(id, email, name, province, description, createdAt, updatedAt);
  }

  public static EmployerDtoIn generateEmployerDto() {
    Faker faker = new Faker();

    String email = faker.internet().emailAddress();
    String name = faker.company().name();
    Integer provinceId = faker.number().numberBetween(1, 10); // Example provinceId
    String description = faker.lorem().sentence();

    return new EmployerDtoIn(email, name, provinceId, description);
  }

  public static UpdateEmployerDtoIn generateUpdateEmployerDto() {
    Faker faker = new Faker();

    String name = faker.company().name();
    Integer provinceId = faker.number().numberBetween(1, 10); // Example provinceId
    String description = faker.lorem().sentence();

    return new UpdateEmployerDtoIn(name, provinceId, description);
  }

  public static PageEmployerDtoIn generateEmployerPageDto() {
    Faker faker = new Faker();

    int pageNumber = faker.random().nextInt(1, MAX_TOTAL_PAGE + 1);
    int pageSize = faker.random().nextInt(1, MAX_PAGE_SIZE + 1);

    return new PageEmployerDtoIn(pageNumber, pageSize);
  }

  public static Job generateJob() {
    Faker faker = new Faker();

    Job job = new Job();

    job.setEmployerId(faker.number().randomDigitNotZero());
    job.setTitle(faker.job().title());
    job.setQuantity(faker.number().randomDigitNotZero());
    job.setDescription(faker.lorem().sentence());
    job.setSalary(faker.number().numberBetween(1000, 5000));
    job.setFields(generateFields());
    job.setProvinces(generateProvinces());
    job.setCreated_at(new Date());
    job.setUpdated_at(new Date());
    job.setExpiredAt(new Date(System.currentTimeMillis() + faker.number().numberBetween(86400000, 604800000))); // Set expiredAt between 1 and 7 days from now

    return job;
  }

  public static JobDtoIn generateJobDto() {
    Faker faker = new Faker();

    JobDtoIn jobDtoIn = new JobDtoIn();
    jobDtoIn.setTitle(faker.job().title());
    jobDtoIn.setQuantity(faker.number().randomDigitNotZero());
    jobDtoIn.setDescription(faker.lorem().sentence());
    jobDtoIn.setFieldIds(generateFieldIds());
    jobDtoIn.setProvinceIds(generateProvinceIds());
    jobDtoIn.setSalary(faker.number().numberBetween(1000, 5000));
    jobDtoIn.setExpiredAt(faker.bool().bool() ? faker.date().future(7, java.util.concurrent.TimeUnit.DAYS) : null);

    return jobDtoIn;
  }

  public static PageJobDtoIn generateJobPageDto() {
    Faker faker = new Faker();

    int pageNumber = faker.random().nextInt(1, MAX_TOTAL_PAGE + 1);
    int pageSize = faker.random().nextInt(1, MAX_PAGE_SIZE + 1);

    return new PageJobDtoIn(pageNumber, pageSize, -1);
  }

  private static List<Integer> generateFieldIds() {
    Faker faker = new Faker();
    List<Integer> fieldIds = new ArrayList<>();
    fieldIds.add(faker.number().numberBetween(10, 30));
    fieldIds.add(faker.number().numberBetween(10, 30));
    return fieldIds;
  }

  private static List<Integer> generateProvinceIds() {
    Faker faker = new Faker();
    List<Integer> provinceIds = new ArrayList<>();
    provinceIds.add(faker.number().numberBetween(1, 20));
    provinceIds.add(faker.number().numberBetween(1, 20));
    return provinceIds;
  }


  private static String generateFields() {
    Faker faker = new Faker();
    StringBuilder fields = new StringBuilder();
    fields.append("-").append(faker.number().numberBetween(1, 10)).append("-");
    fields.append(faker.number().numberBetween(1, 10)).append("-");
    return fields.toString();
  }

  private static String generateProvinces() {
    Faker faker = new Faker();
    StringBuilder provinces = new StringBuilder();
    provinces.append("-").append(faker.number().numberBetween(1, 10)).append("-");
    provinces.append(faker.number().numberBetween(1, 10)).append("-");
    return provinces.toString();
  }

  public static User generateSampleActiveUser(String rawPassword) {
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    Faker faker = new Faker();

    User user = new User();
    user.setUsername(faker.name().username());
    String hashedPassword = passwordEncoder.encode(rawPassword);
    user.setPassword(hashedPassword);
    user.setActive(true);

    return user;
  }

}






