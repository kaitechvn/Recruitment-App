package com.example.recruitment.api.service.analytic;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class AnalyticResponse {
  private long sumEmployerCount;
  private long sumJobCount;
  private long sumSeekerCount;
  private long sumResumeCount;
  private List<AnalyticData> analyticData;
}
