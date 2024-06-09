package com.example.recruitment.api.service.analytic;

import com.example.recruitment.api.dto.out.analytic.Analytic;

import java.util.Date;

public interface AnalyticService {
  Analytic getAnalyticBetweenDates(Date fromDate, Date toDate);
}
