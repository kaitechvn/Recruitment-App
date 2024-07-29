package com.example.recruitment.api.dto.in;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSeekerDto {
  @NotBlank
  private String name;

  @NotEmpty
  private String birthday;

  private String address;

  @NotNull
  private Integer provinceId;
}
