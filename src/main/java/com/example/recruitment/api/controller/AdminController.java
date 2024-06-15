package com.example.recruitment.api.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Hidden
@PreAuthorize("hasRole('BOSS')")
@RestController
@RequestMapping("/admin")
public class AdminController {

  @GetMapping()
  public String adminDashboard() {
    return "Welcome to Admin Home!";
  }
}
