package com.example.recruitment.service;

import com.example.recruitment.common.dto.CommonDtoOut;
import com.example.recruitment.common.dto.PageDtoOut;
import com.example.recruitment.dto.in.ResumeDtoIn;
import com.example.recruitment.dto.in.page.PageResumeDtoIn;
import com.example.recruitment.dto.in.page.PageSeekerDtoIn;
import com.example.recruitment.dto.in.SeekerDtoIn;
import com.example.recruitment.dto.in.UpdateResumeDtoIn;
import com.example.recruitment.dto.out.pagedata.DataResume;
import com.example.recruitment.dto.out.ResumeDtoOut;
import org.springframework.stereotype.Service;

@Service
public interface ResumeService {
  CommonDtoOut<ResumeDtoOut> get(Integer id);
  CommonDtoOut<ResumeDtoOut> create(ResumeDtoIn dto);
  CommonDtoOut<ResumeDtoOut> update(Integer id, UpdateResumeDtoIn dto);
  CommonDtoOut<ResumeDtoOut> delete(Integer id);
  CommonDtoOut<PageDtoOut<DataResume>> list(PageResumeDtoIn dto);
}
