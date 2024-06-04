package com.example.recruitment.service;

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
  ResumeDtoOut get(Integer id);
  ResumeDtoOut create(ResumeDtoIn dto);
  ResumeDtoOut update(Integer id, UpdateResumeDtoIn dto);
  ResumeDtoOut delete(Integer id);
  PageDtoOut<DataResume> list(PageResumeDtoIn dto);
}
