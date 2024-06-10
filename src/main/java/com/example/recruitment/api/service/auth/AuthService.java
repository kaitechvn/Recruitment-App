package com.example.recruitment.api.service.auth;


import com.example.recruitment.api.dto.in.AuthLoginDtoIn;
import com.example.recruitment.api.dto.out.AuthLoginDtoOut;

public interface AuthService {
  AuthLoginDtoOut login(AuthLoginDtoIn loginDtoIn);
}
