package com.example.recruitment.service;


import com.example.recruitment.common.dto.CommonDtoOut;
import com.example.recruitment.common.dto.PageDtoOut;
import com.example.recruitment.dto.in.page.PageSeekerDtoIn;
import com.example.recruitment.dto.in.SeekerDtoIn;
import com.example.recruitment.dto.out.pagedata.DataSeeker;
import com.example.recruitment.dto.out.SeekerDtoOut;

public interface SeekerService {
  CommonDtoOut<SeekerDtoOut> get(Integer id);
  CommonDtoOut<SeekerDtoOut> create(SeekerDtoIn dto);
  CommonDtoOut<SeekerDtoOut> update(Integer id, SeekerDtoIn dto);
  CommonDtoOut<SeekerDtoOut> delete(Integer id);
  CommonDtoOut<PageDtoOut<DataSeeker>> list(PageSeekerDtoIn dto);

}
