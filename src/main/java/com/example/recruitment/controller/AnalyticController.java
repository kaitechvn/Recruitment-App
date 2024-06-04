package com.example.recruitment.controller;

import com.example.recruitment.dto.out.analytic.Analytic;
import com.example.recruitment.service.AnalyticServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class AnalyticController {

  @Autowired
  private AnalyticServiceImpl analyticService;

  @GetMapping("/analytic")
  public Analytic getAnalytic(@RequestParam("fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fromDate,
                              @RequestParam("toDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date toDate) {


    return analyticService.getAnalyticBetweenDates(fromDate, toDate);
  }
}
