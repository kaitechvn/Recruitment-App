package com.example.recruitment.api.dto.out.pagedata;

import com.example.recruitment.common.holder.Holder;
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

  public static DataSeeker fromSeeker(Seeker seeker) {
    ProvinceRepository provinceRepository = Holder.getProvinceRepository();
    String provinceName = provinceRepository.findById(seeker.getProvince())
      .map(Province::getName)
      .orElse(null);

    return DataSeeker.builder()
      .id(seeker.getId())
      .name(seeker.getName())
      .birthday(seeker.getBirthday())
      .address(seeker.getAddress())
      .provinceId(seeker.getProvince())
      .provinceName(provinceName)
      .build();
  }
}
