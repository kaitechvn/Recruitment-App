package com.example.recruitment.api.dto.out.analytic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Analytic {
  private Integer numEmployer;
  private Integer numJob;
  private Integer numSeeker;
  private Integer numResume;
  private List<Element> chart = new ArrayList<>();
}
