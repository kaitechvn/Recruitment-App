package com.example.recruitment.api.service.analytic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class AnalyticData {
  private LocalDate date;
  private long employerCount;
  private long jobCount;
  private long seekerCount;
  private long resumeCount;

}
