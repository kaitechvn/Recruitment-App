package com.example.recruitment.api.dto.out;

import com.example.recruitment.api.entity.Field;
import com.example.recruitment.api.entity.Province;
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

public class JobDtoOut {
  private Integer id;
  private String title;
  private Integer quantity;
  private String description;
  private List<Field> fields;
  private List<Province> provinces;
  private Integer salary;
  private Date expiredAt;
  private Integer employerId;
  private String employerName;
}
