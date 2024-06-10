package com.example.recruitment.api.service.resume;

import com.example.recruitment.common.dto.PageDtoOut;
import com.example.recruitment.api.dto.in.ResumeDtoIn;
import com.example.recruitment.api.dto.in.page.PageResumeDtoIn;
import com.example.recruitment.api.dto.in.UpdateResumeDtoIn;
import com.example.recruitment.api.dto.out.pagedata.DataResume;
import com.example.recruitment.api.dto.out.ResumeDtoOut;
import org.springframework.stereotype.Service;

@Service
public interface ResumeService {
  ResumeDtoOut get(Integer id);
  ResumeDtoOut create(ResumeDtoIn dto);
  ResumeDtoOut update(Integer id, UpdateResumeDtoIn dto);
  ResumeDtoOut delete(Integer id);
  PageDtoOut<DataResume> list(PageResumeDtoIn dto);
}
