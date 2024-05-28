package com.example.recruitment.dto.out;

import com.example.recruitment.common.data_transform.Converter;
import com.example.recruitment.common.holder.Holder;
import com.example.recruitment.entity.Field;
import com.example.recruitment.entity.Province;
import com.example.recruitment.entity.Resume;
import com.example.recruitment.entity.Seeker;
import com.example.recruitment.repository.FieldRepository;
import com.example.recruitment.repository.ProvinceRepository;
import com.example.recruitment.repository.SeekerRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ResumeDtoOut {
  private Integer id;
  private Integer seekerId;
  private String seekerName;
  private String careerObj;
  private String title;
  private Integer salary;
  private List<Field> fields;
  private List<Province> provinces;

  public static ResumeDtoOut fromResume(Resume resume){
    SeekerRepository seekerRepository = Holder.getSeekerRepository();
    ProvinceRepository provinceRepository = Holder.getProvinceRepository();
    FieldRepository fieldRepository = Holder.getFieldRepository();

    String seekerName = seekerRepository.findById(resume.getSeekerId())
      .map(Seeker::getName)
      .orElse(null);

    List<Integer> provinceIds = Converter.extractIdFromStringDb(resume.getProvinces());
    List<Province> province = provinceRepository.findAllById(provinceIds);

    List<Integer> fieldIds = Converter.extractIdFromStringDb(resume.getFields());
    List<Field> field = fieldRepository.findAllById(fieldIds);

    return ResumeDtoOut.builder()
      .id(resume.getId())
      .seekerId(resume.getSeekerId())
      .seekerName(seekerName)
      .careerObj(resume.getCareerObj())
      .title(resume.getTitle())
      .salary(resume.getSalary())
      .fields(field)
      .provinces(province)
      .build();
  }
}
