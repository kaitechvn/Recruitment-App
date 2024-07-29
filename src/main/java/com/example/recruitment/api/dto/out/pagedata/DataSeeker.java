package com.example.recruitment.api.dto.out.pagedata;

import com.example.recruitment.api.entity.Province;
import com.example.recruitment.api.entity.Seeker;
import com.example.recruitment.api.repository.ProvinceRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class DataSeeker {
  private Integer id;
  private String name;
  private String birthday;
  private String address;
  private Integer provinceId;
  private String provinceName;
}
