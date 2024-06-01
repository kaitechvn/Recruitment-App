package com.example.recruitment.controller;


import com.example.recruitment.dto.in.AuthLoginDtoIn;
import com.example.recruitment.dto.out.AuthLoginDtoOut;
import com.example.recruitment.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")

public class AuthController {

  @Autowired
  private AuthService authService;

  @PostMapping(value = "/login")
  public ResponseEntity<AuthLoginDtoOut> login(@RequestBody @Valid AuthLoginDtoIn loginDtoIn) {
    return new ResponseEntity<>(
      this.authService.login(loginDtoIn), HttpStatus.OK
    );
  }
}


