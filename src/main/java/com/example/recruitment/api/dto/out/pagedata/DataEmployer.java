package com.example.recruitment.api.dto.out.pagedata;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataEmployer {
    private Integer id;
    private String email;
    private String name;
    private Integer provinceId;
    private String provinceName;

}
