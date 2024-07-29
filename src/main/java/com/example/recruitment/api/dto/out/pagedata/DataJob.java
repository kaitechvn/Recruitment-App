package com.example.recruitment.api.dto.out.pagedata;

import com.example.recruitment.api.entity.Employer;
import com.example.recruitment.api.entity.Job;
import com.example.recruitment.api.repository.EmployerRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class DataJob {
  private Integer id;
  private String title;
  private Integer quantity;
  private Integer salary;
  private Date expiredAt;
  private Integer employerId;
  private String employerName;
}
