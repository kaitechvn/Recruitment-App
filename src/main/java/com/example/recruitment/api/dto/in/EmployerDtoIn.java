package com.example.recruitment.api.dto.in;
import com.example.recruitment.api.validator.SensitiveWord;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployerDtoIn {
    @NotEmpty
    @Email
    @Size(max = 255)
    private String email;

    @NotBlank
    @Size (max = 255)
    private String name;

    @NotNull
    @Positive(message = "Must be positive")
    private Integer provinceId;

    @SensitiveWord(lang = "vn")
    private String description;



}
