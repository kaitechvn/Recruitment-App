package com.example.recruitment.api.dto.in;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SeekerDtoIn {
  @NotBlank
  private String name;

  @NotEmpty
  private String birthday;

  private String address;

  private Integer province;
}
