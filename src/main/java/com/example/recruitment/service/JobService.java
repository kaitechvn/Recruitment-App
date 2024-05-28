package com.example.recruitment.service;

import com.example.recruitment.common.dto.CommonDtoOut;
import com.example.recruitment.common.dto.PageDtoOut;
import com.example.recruitment.dto.in.JobDtoIn;
import com.example.recruitment.dto.in.page.PageJobDtoIn;
import com.example.recruitment.dto.out.pagedata.DataJob;
import com.example.recruitment.dto.out.JobDtoOut;

public interface JobService {
  CommonDtoOut<JobDtoOut> get(Integer id);
  CommonDtoOut<JobDtoOut> create(JobDtoIn dto);
  CommonDtoOut<JobDtoOut> update(Integer id, JobDtoIn dto);
  CommonDtoOut<JobDtoOut> delete(Integer id);
  CommonDtoOut<PageDtoOut<DataJob>> list(PageJobDtoIn dto);

}
