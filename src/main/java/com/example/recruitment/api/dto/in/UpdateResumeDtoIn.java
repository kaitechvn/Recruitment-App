package com.example.recruitment.api.dto.in;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateResumeDtoIn {
  private Integer id;
  private String careerObj;
  private Integer salary;
  private String title;
  private List<Integer> fieldIds;
  private List<Integer> provinceIds;
}
