package com.example.recruitment.api.dto.in;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEmployerDtoIn {
    @NotBlank
    @Size( max = 255)
    @Nullable
    private String name;

    @Nullable
    @Positive
    private Integer province;

    private String description;
}
