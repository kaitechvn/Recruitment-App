package com.example.recruitment.api.dto.out;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class SeekerDtoOut {
  private Integer id;
  private String name;
  private String birthday;
  private String address;
  private Integer provinceId;
  private String provinceName;

}
