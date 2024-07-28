package com.example.recruitment.api.mapper;

import com.example.recruitment.api.dto.in.ResumeDtoIn;
import com.example.recruitment.api.dto.in.update.UpdateEmployerDto;
import com.example.recruitment.api.dto.in.update.UpdateResumeDto;
import com.example.recruitment.api.dto.out.ResumeDtoOut;
import com.example.recruitment.api.dto.out.pagedata.DataResume;
import com.example.recruitment.api.entity.Employer;
import com.example.recruitment.api.entity.Resume;
import com.example.recruitment.common.data_transform.Converter;
import org.mapstruct.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ResumeMapper {
  ResumeMapper INSTANCE = Mappers.getMapper(ResumeMapper.class);

  @Mapping(source = "provinceIds", target = "provinces", qualifiedByName = "ListToStringDb")
  @Mapping(source = "fieldIds", target = "fields", qualifiedByName = "ListToStringDb")
  Resume toResume(ResumeDtoIn resumeDtoIn);

  @Mapping(target = "fields", ignore = true)
  @Mapping(target = "provinces", ignore = true)
  @Mapping(target = "seekerName", ignore = true)
  ResumeDtoOut toResumeDtoOut(Resume resume);

  DataResume toDataResume(Resume resume);

  void toUpdateResume(UpdateResumeDto dto, @MappingTarget Resume resume);

  @Named("ListToStringDb")
  static String ListToStringDb(List<Integer> list) {
    return Converter.ListToStringDb(list);
  }
}
