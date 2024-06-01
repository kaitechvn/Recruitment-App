package com.example.recruitment.service;


import com.example.recruitment.dto.in.AuthLoginDtoIn;
import com.example.recruitment.dto.out.AuthLoginDtoOut;

public interface AuthService {
  AuthLoginDtoOut login(AuthLoginDtoIn loginDtoIn);
}
