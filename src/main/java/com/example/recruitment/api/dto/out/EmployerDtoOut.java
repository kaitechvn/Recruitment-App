package com.example.recruitment.api.dto.out;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployerDtoOut {
    private Integer id;
    private String email;
    private String name;
    private Integer provinceId;
    private String provinceName;
    private String description;
}
