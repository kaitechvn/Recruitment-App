package com.example.recruitment.api.controller;

import com.example.recruitment.api.service.analytic.AnalyticResponse;
import com.example.recruitment.api.service.analytic.AnalyticServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;


@Tag(name = "Analytic", description = "Insight analytic")
@RestController
public class AnalyticController {

  @Autowired
  private AnalyticServiceImpl analyticService;

  @GetMapping("/analytic")
  public AnalyticResponse getAnalytic(@RequestParam("fromDate") LocalDate fromDate,
                                      @RequestParam("toDate")  LocalDate toDate) {

    return analyticService.getAnalytics(fromDate, toDate);
  }
}
