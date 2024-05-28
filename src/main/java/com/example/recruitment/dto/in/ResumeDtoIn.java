package com.example.recruitment.dto.in;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@NotNull
public class ResumeDtoIn {
  private Integer seekerId;
  private String careerObj;
  private String title;
  private Integer salary;
  private List<Integer> fieldIds;
  private List<Integer> provinceIds;
}
