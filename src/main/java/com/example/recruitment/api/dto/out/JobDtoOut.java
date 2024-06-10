package com.example.recruitment.api.dto.out;

import com.example.recruitment.common.data_transform.Converter;
import com.example.recruitment.common.holder.Holder;
import com.example.recruitment.api.entity.Employer;
import com.example.recruitment.api.entity.Field;
import com.example.recruitment.api.entity.Job;
import com.example.recruitment.api.entity.Province;
import com.example.recruitment.api.repository.EmployerRepository;
import com.example.recruitment.api.repository.FieldRepository;
import com.example.recruitment.api.repository.ProvinceRepository;
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


  public static JobDtoOut fromJob(Job job){
    ProvinceRepository provinceRepository = Holder.getProvinceRepository();
    FieldRepository fieldRepository = Holder.getFieldRepository();
    EmployerRepository employerRepository = Holder.getEmployerRepository();

    List<Integer> provinceIds = Converter.extractIdFromStringDb(job.getProvinces());
    List<Province> province = provinceRepository.findAllById(provinceIds);

    List<Integer> fieldIds = Converter.extractIdFromStringDb(job.getFields());
    List<Field> field = fieldRepository.findAllById(fieldIds);

    String employerName = employerRepository.findById(job.getEmployerId())
      .map(Employer::getName)
      .orElse(null);

    return JobDtoOut.builder()
      .id(job.getId())
      .title(job.getTitle())
      .quantity(job.getQuantity())
      .description(job.getDescription())
      .salary(job.getSalary())
      .expiredAt(job.getExpiredAt())
      .employerId(job.getEmployerId())
      .provinces(province)
      .fields(field)
      .employerName(employerName)
      .build();
  }
}
