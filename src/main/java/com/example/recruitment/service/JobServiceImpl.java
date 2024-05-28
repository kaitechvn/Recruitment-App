package com.example.recruitment.service;

import com.example.recruitment.common.data_transform.Update;
import com.example.recruitment.common.dto.CommonDtoOut;
import com.example.recruitment.common.dto.PageDtoOut;
import com.example.recruitment.common.error.ErrorCode;
import com.example.recruitment.common.error.ServiceValid;
import com.example.recruitment.common.exception.ApiException;
import com.example.recruitment.dto.in.JobDtoIn;
import com.example.recruitment.dto.in.page.PageJobDtoIn;
import com.example.recruitment.dto.out.pagedata.DataJob;
import com.example.recruitment.dto.out.JobDtoOut;
import com.example.recruitment.entity.Employer;
import com.example.recruitment.entity.Job;
import com.example.recruitment.repository.EmployerRepository;
import com.example.recruitment.repository.FieldRepository;
import com.example.recruitment.repository.JobRepository;
import com.example.recruitment.repository.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@CacheConfig(cacheNames = "jobs")
@Service
public class JobServiceImpl implements JobService {
  @Autowired
  private ProvinceRepository provinceRepository;

  @Autowired
  private FieldRepository fieldRepository;

  @Autowired
  private JobRepository jobRepository;

  @Autowired
  private EmployerRepository employerRepository;

  @Cacheable("jobs")
  @Override
  public CommonDtoOut<JobDtoOut> get(Integer id) {
      Job job = this.jobRepository.findById(id)
      .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "job not found"));

      return CommonDtoOut.success(JobDtoOut.fromJob(job));

  }

  @Override
  public CommonDtoOut<JobDtoOut> create(JobDtoIn dto) {
    String errorMessage = ServiceValid.validateIds(
      dto.getProvinceIds(),
      dto.getFieldIds(),
      dto.getEmployerId(),
      provinceRepository,
      fieldRepository,
      employerRepository::findById,
      "Employer"
    );

    if (!errorMessage.isEmpty()) {
      throw new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, errorMessage);
    }

    else {
      Job addJob = this.jobRepository.save(Job.fromDto(dto));
      JobDtoOut jobOut = JobDtoOut.fromJob(addJob);
      return CommonDtoOut.create(jobOut);
    }
  }

  @Override
  public CommonDtoOut<JobDtoOut> update(Integer id, JobDtoIn dto) {
    Job updatingJob = this.jobRepository.findById(id)
      .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "job not found"));

    String errorMessage = ServiceValid.validateIds(
      dto.getProvinceIds(),
      dto.getFieldIds(),
      dto.getEmployerId(),
      provinceRepository,
      fieldRepository,
      employerRepository::findById,
      "Employer"
    );

    if (!errorMessage.isEmpty()) {
      throw new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, errorMessage);
    }
      else {

        Update.copyIgnoreNull(Job.fromDto(dto), updatingJob); // not null field update
        Job toUpdateJob = this.jobRepository.save(updatingJob);
        JobDtoOut jobOut = JobDtoOut.fromJob(toUpdateJob);

        if (dto.getProvinceIds() != null) {
          jobOut.setProvinces(this.provinceRepository.findAllById(dto.getProvinceIds()));
        }

        if (dto.getFieldIds() != null ) {
          jobOut.setFields(this.fieldRepository.findAllById(dto.getFieldIds()));
        }

        if (dto.getEmployerId() != null) {
          String employerName = this.employerRepository.findById(dto.getEmployerId())
                                .map(Employer::getName)
                                .orElse(null);
          jobOut.setEmployerName(employerName);
      }

        return CommonDtoOut.success(jobOut);
      }
  }

  @Override
  public CommonDtoOut<JobDtoOut> delete(Integer id) {
    Job job = this.jobRepository.findById(id)
      .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "job not found"));

      JobDtoOut jobOut = JobDtoOut.fromJob(job);
      this.jobRepository.delete(job);
      return CommonDtoOut.success(jobOut);
    }

  @Override
  public CommonDtoOut<PageDtoOut<DataJob>> list(PageJobDtoIn dto) {
    Pageable paging = PageRequest.of(dto.getPage() - 1, dto.getPageSize(),
      Sort.by("expiredAt").descending());

    Page<Job> pagedResult = dto.getEmployerId() == -1 ?
      jobRepository.findAll(paging) :
      jobRepository.findAllByEmployerId(dto.getEmployerId(), paging);

    List<DataJob> dataJobList = pagedResult.getContent().stream()
      .map(DataJob::fromJob).sorted(Comparator
      .comparing(DataJob::getExpiredAt, Comparator.nullsLast(Comparator.reverseOrder()))
      .thenComparing(DataJob::getEmployerName, Comparator.nullsLast(Comparator.naturalOrder())))
      .collect(Collectors.toList());


    return CommonDtoOut.success(
      PageDtoOut.from(dto.getPage(), dto.getPageSize(), pagedResult.getTotalElements(),
       dataJobList)
    );
  }

}
