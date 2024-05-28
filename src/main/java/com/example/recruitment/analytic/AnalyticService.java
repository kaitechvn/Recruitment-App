package com.example.recruitment.analytic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AnalyticService {
  @Autowired
  private AnalyticRepository analyticRepository;

  public Analytic getAnalyticBetweenDates(Date fromDate, Date toDate) {
    return analyticRepository.getAnalytic(fromDate, toDate);
  }
}
