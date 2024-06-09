package com.example.recruitment.api.service.seeker;


import com.example.recruitment.common.dto.PageDtoOut;
import com.example.recruitment.api.dto.in.page.PageSeekerDtoIn;
import com.example.recruitment.api.dto.in.SeekerDtoIn;
import com.example.recruitment.api.dto.out.pagedata.DataSeeker;
import com.example.recruitment.api.dto.out.SeekerDtoOut;

public interface SeekerService {
  SeekerDtoOut get(Integer id);
  SeekerDtoOut create(SeekerDtoIn dto);
  SeekerDtoOut update(Integer id, SeekerDtoIn dto);
  SeekerDtoOut delete(Integer id);
  PageDtoOut<DataSeeker> list(PageSeekerDtoIn dto);

}
