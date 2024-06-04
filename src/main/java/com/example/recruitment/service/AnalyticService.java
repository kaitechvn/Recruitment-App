package com.example.recruitment.service;

import com.example.recruitment.dto.out.analytic.Analytic;

import java.util.Date;

public interface AnalyticService {
  Analytic getAnalyticBetweenDates(Date fromDate, Date toDate);
}
