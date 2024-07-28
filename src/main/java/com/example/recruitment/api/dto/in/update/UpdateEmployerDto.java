package com.example.recruitment.api.dto.in.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEmployerDto {
  @NotBlank
  @Size(max = 255)
  private String name;

  @NotNull
  @Positive(message = "Must be positive")
  private Integer provinceId;

  private String description;
}
