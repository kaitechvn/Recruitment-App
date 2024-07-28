package com.example.recruitment.api.dto.out.pagedata;

import com.example.recruitment.api.entity.Resume;
import com.example.recruitment.api.entity.Seeker;
import com.example.recruitment.api.repository.SeekerRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class DataResume {
  private Integer id;
  private Integer seekerId;
  private String seekerName;
  private Integer salary;
  private String title;
  private String careerObj;
}
