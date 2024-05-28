package com.example.recruitment.dto.out;

import com.example.recruitment.common.holder.Holder;
import com.example.recruitment.entity.Province;
import com.example.recruitment.entity.Seeker;
import com.example.recruitment.repository.ProvinceRepository;
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

  public static SeekerDtoOut fromSeeker(Seeker seeker) {
    ProvinceRepository provinceRepository = Holder.getProvinceRepository();
    String provinceName = provinceRepository.findById(seeker.getProvince())
      .map(Province::getName)
      .orElse(null);

    return SeekerDtoOut.builder()
      .id(seeker.getId())
      .name(seeker.getName())
      .birthday(seeker.getBirthday())
      .address(seeker.getAddress())
      .provinceId(seeker.getProvince())
      .provinceName(provinceName)
      .build();
  }
}
