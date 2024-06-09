package com.example.recruitment.api.dto.in;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class JobDtoIn {

    private String title;
    @NotNull
    private Integer employerId;
    private Integer quantity;
    private String description;
    @NotEmpty
    private List<Integer> fieldIds ;
    @NotEmpty
    private List<Integer> provinceIds;
    private Integer salary;
    private Date expiredAt;
}
