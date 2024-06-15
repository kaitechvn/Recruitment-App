package com.example.recruitment.api.controller;

import com.example.recruitment.api.dto.out.analytic.Analytic;
import com.example.recruitment.api.service.analytic.AnalyticServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Tag(name = "Analytic", description = "Insight analytic")
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
