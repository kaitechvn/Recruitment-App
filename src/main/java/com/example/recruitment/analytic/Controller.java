package com.example.recruitment.analytic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@RestController
public class Controller {

  @Autowired
  private AnalyticService analyticService;

  @GetMapping("/analytic")
  public Analytic getAnalytic(@RequestParam("fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fromDate,
                              @RequestParam("toDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date toDate) {


    return analyticService.getAnalyticBetweenDates(fromDate, toDate);
  }
}
