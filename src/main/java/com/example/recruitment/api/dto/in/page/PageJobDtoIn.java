package com.example.recruitment.api.dto.in.page;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class PageJobDtoIn {
  @NotNull
  @Positive
  private Integer page;

  @NotNull
  @Positive
  @Max(500)
  private Integer pageSize;

  @NotNull
  private Integer employerId;
}
