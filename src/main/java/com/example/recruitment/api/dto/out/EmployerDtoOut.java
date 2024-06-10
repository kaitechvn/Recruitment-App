package com.example.recruitment.api.dto.out;

import com.example.recruitment.common.holder.Holder;
import com.example.recruitment.api.entity.Employer;
import com.example.recruitment.api.entity.Province;
import com.example.recruitment.api.repository.ProvinceRepository;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployerDtoOut {
    private Integer id;
    private String email;
    private String name;
    private Integer provinceId;
    private String provinceName;
    private String description;

    public static EmployerDtoOut fromEmployer(Employer employer){
      ProvinceRepository provinceRepository = Holder.getProvinceRepository();
      String provinceName = provinceRepository.findById(employer.getProvince())
        .map(Province::getName)
        .orElse(null);

        return EmployerDtoOut.builder()
                .id(employer.getId())
                .email(employer.getEmail())
                .name(employer.getName())
                .provinceId(employer.getProvince())
                .description(employer.getDescription())
                .provinceName(provinceName)
                .build();
    }
}
