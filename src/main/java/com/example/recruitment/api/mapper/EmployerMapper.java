package com.example.recruitment.api.mapper;

import com.example.recruitment.api.dto.in.EmployerDtoIn;
import com.example.recruitment.api.dto.in.update.UpdateEmployerDto;
import com.example.recruitment.api.dto.out.EmployerDtoOut;
import com.example.recruitment.api.dto.out.pagedata.DataEmployer;
import com.example.recruitment.api.entity.Employer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployerMapper {
  EmployerMapper INSTANCE = Mappers.getMapper(EmployerMapper.class );

  @Mapping(source = "provinceId", target = "province")
  Employer toEmployer(EmployerDtoIn employerDtoIn);

  @Mapping(source = "province", target = "provinceId")
  @Mapping(target = "provinceName", ignore = true)
  EmployerDtoOut toEmployerDtoOut(Employer employer);

  @Mapping(source = "provinceId", target = "province")
  @Mapping(target = "description", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void toUpdateEmployer(UpdateEmployerDto dto, @MappingTarget Employer employer);

  DataEmployer toDataEmployer(Employer employer);


}
