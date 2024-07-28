package com.example.recruitment.api.service.analytic;

import java.time.LocalDate;

public interface AnalyticService {
  AnalyticResponse getAnalytics(LocalDate fromDate, LocalDate toDate);
}
