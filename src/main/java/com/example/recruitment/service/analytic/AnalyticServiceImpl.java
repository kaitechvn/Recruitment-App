package com.example.recruitment.service.analytic;

import com.example.recruitment.dto.out.analytic.Analytic;
import com.example.recruitment.repository.AnalyticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AnalyticServiceImpl implements AnalyticService {

  @Autowired
  private AnalyticRepository analyticRepository;

  public Analytic getAnalyticBetweenDates(Date fromDate, Date toDate) {
    return analyticRepository.getAnalytic(fromDate, toDate);
  }
}
