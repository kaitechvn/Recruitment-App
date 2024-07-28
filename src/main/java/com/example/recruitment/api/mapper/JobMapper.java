package com.example.recruitment.api.mapper;

import com.example.recruitment.api.dto.in.JobDtoIn;
import com.example.recruitment.api.dto.in.UpdateJobDto;
import com.example.recruitment.api.dto.in.update.UpdateEmployerDto;
import com.example.recruitment.api.dto.out.JobDtoOut;
import com.example.recruitment.api.dto.out.pagedata.DataJob;
import com.example.recruitment.api.entity.Employer;
import com.example.recruitment.api.entity.Job;
import com.example.recruitment.common.data_transform.Converter;
import org.mapstruct.*;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface JobMapper {
  JobMapper INSTANCE = Mappers.getMapper(JobMapper.class);

  @Mapping(source = "provinceIds", target = "provinces", qualifiedByName = "ListToStringDb")
  @Mapping(source = "fieldIds", target = "fields", qualifiedByName = "ListToStringDb")
  Job toJob(JobDtoIn jobDtoIn);

  @Mapping(target = "fields", ignore = true)
  @Mapping(target = "provinces", ignore = true)
  @Mapping(target = "employerName", ignore = true)
  JobDtoOut toJobDtoOut(Job job);

  DataJob toDataJob(Job job);

  void toUpdateJob(UpdateJobDto dto, @MappingTarget Job job);

  @Named("ListToStringDb")
  static String ListToStringDb(List<Integer> list) {
    return Converter.ListToStringDb(list);
  }

}
