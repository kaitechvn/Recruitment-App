package com.example.recruitment.api.service.job;

import com.example.recruitment.common.dto.PageDtoOut;
import com.example.recruitment.api.dto.in.JobDtoIn;
import com.example.recruitment.api.dto.in.page.PageJobDtoIn;
import com.example.recruitment.api.dto.out.pagedata.DataJob;
import com.example.recruitment.api.dto.out.JobDtoOut;

public interface JobService {
  JobDtoOut get(Integer id);
  JobDtoOut create(JobDtoIn dto);
  JobDtoOut update(Integer id, JobDtoIn dto);
  JobDtoOut delete(Integer id);
  PageDtoOut<DataJob> list(PageJobDtoIn dto);

}
