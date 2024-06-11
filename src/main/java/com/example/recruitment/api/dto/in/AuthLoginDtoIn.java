package com.example.recruitment.api.dto.in;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthLoginDtoIn {
  @NotEmpty
  private String username;

  @NotEmpty
  private String password;
}
