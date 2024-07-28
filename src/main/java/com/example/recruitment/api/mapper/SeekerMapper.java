package com.example.recruitment.api.mapper;

import com.example.recruitment.api.dto.in.SeekerDtoIn;
import com.example.recruitment.api.dto.in.UpdateSeekerDto;
import com.example.recruitment.api.dto.in.update.UpdateEmployerDto;
import com.example.recruitment.api.dto.out.SeekerDtoOut;
import com.example.recruitment.api.dto.out.pagedata.DataSeeker;
import com.example.recruitment.api.entity.Employer;
import com.example.recruitment.api.entity.Seeker;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SeekerMapper {
  SeekerMapper INSTANCE = Mappers.getMapper(SeekerMapper.class);

  @Mapping(source = "provinceId", target = "province")
  Seeker toSeeker(SeekerDtoIn seekerDtoIn);

  @Mapping(source = "province", target = "provinceId")
  @Mapping(target = "provinceName", ignore = true)
  SeekerDtoOut toSeekerDtoOut(Seeker seeker);

  @Mapping(source = "provinceId", target = "province")
  @Mapping(target = "address", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void toUpdateSeeker(UpdateSeekerDto dto, @MappingTarget Seeker seeker);

  DataSeeker toDataSeeker(Seeker seeker);
}
