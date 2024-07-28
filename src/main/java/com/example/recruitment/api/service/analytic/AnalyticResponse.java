package com.example.recruitment.api.service.analytic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class AnalyticResponse {
  private long sumEmployerCount;
  private long sumJobCount;
  private long sumSeekerCount;
  private long sumResumeCount;
  private List<AnalyticData> analyticData;
}
