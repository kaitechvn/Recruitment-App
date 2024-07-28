package com.example.recruitment.api.service.job;

import com.example.recruitment.api.entity.Employer;
import com.example.recruitment.api.entity.Field;
import com.example.recruitment.api.entity.Province;
import com.example.recruitment.api.mapper.JobMapper;
import com.example.recruitment.common.data_transform.Converter;
import com.example.recruitment.common.dto.PageDtoOut;
import com.example.recruitment.common.code.ErrorCode;
import com.example.recruitment.common.exception.ApiException;
import com.example.recruitment.api.dto.in.JobDtoIn;
import com.example.recruitment.api.dto.in.page.PageJobDtoIn;
import com.example.recruitment.api.dto.out.pagedata.DataJob;
import com.example.recruitment.api.dto.out.JobDtoOut;
import com.example.recruitment.api.entity.Job;

import com.example.recruitment.api.repository.EmployerRepository;
import com.example.recruitment.api.repository.FieldRepository;
import com.example.recruitment.api.repository.JobRepository;
import com.example.recruitment.api.repository.ProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {

  private final JobRepository jobRepository;
  private final ProvinceRepository provinceRepository;
  private final FieldRepository fieldRepository;
  private final EmployerRepository employerRepository;
  private final JobMapper jobMapper = JobMapper.INSTANCE;

  @Autowired
  public JobServiceImpl(JobRepository jobRepository,
                        ProvinceRepository provinceRepository,
                        FieldRepository fieldRepository,
                        EmployerRepository employerRepository
  ) {
    this.jobRepository = jobRepository;
    this.provinceRepository = provinceRepository;
    this.fieldRepository = fieldRepository;
    this.employerRepository = employerRepository;
  }

  @Override
  public JobDtoOut get(Integer id) {
    Job job = this.jobRepository.findById(id)
      .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "job not found"));

    return mapJobDtoOut(job);

  }

  @Override
  public JobDtoOut create(JobDtoIn dto) {

    validateDto(dto);

    Job addJob = this.jobRepository.save(jobMapper.toJob(dto));
    return mapJobDtoOut(addJob);
  }


  @Override
  public JobDtoOut update(Integer id, JobDtoIn dto) {
    Job updatingJob = this.jobRepository.findById(id)
      .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "job not found"));

    validateDto(dto);

    Job toUpdateJob = this.jobRepository.save(updatingJob);

    return mapJobDtoOut(toUpdateJob);
  }


  @Override
  public JobDtoOut delete(Integer id) {
    Job job = this.jobRepository.findById(id)
      .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "job not found"));

    this.jobRepository.delete(job);
    return mapJobDtoOut(job);
  }

  @Override
  public PageDtoOut<DataJob> list(PageJobDtoIn dto) {
    Pageable paging = PageRequest.of(dto.getPage() - 1, dto.getPageSize(),
      Sort.by("expiredAt").descending());

    Page<Job> pagedResult = dto.getEmployerId() == -1 ?
      jobRepository.findAll(paging) :
      jobRepository.findAllByEmployerId(dto.getEmployerId(), paging);

    List<DataJob> dataJobList = pagedResult.getContent().stream()
      .map(jobMapper::toDataJob).sorted(Comparator
        .comparing(DataJob::getExpiredAt, Comparator.nullsLast(Comparator.reverseOrder()))
        .thenComparing(DataJob::getEmployerName, Comparator.nullsLast(Comparator.naturalOrder())))
      .collect(Collectors.toList());


    return PageDtoOut.from(dto.getPage(), dto.getPageSize(), pagedResult.getTotalElements(),
      dataJobList);
  }

  private void validateDto(JobDtoIn dto) {
    for (Integer pid : dto.getProvinceIds()) {
      if (!provinceRepository.existsById(pid)) {
        throw new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "province ID " + pid + " not found");
      }
    }

    for (Integer fid : dto.getFieldIds()) {
      if (!fieldRepository.existsById(fid)) {
        throw new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "field ID " + fid + " not found");
      }
    }

    if (!employerRepository.existsById(dto.getEmployerId())) {
      throw new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "employer not found");
    }
  }

    private JobDtoOut mapJobDtoOut(Job job) {

    JobDtoOut jobDtoOut = jobMapper.toJobDtoOut(job);

    List<Integer> provinceIds = Converter.extractIdFromStringDb(job.getProvinces());
    List<Province> province = provinceRepository.findAllById(provinceIds);

    List<Integer> fieldIds = Converter.extractIdFromStringDb(job.getFields());
    List<Field> field = fieldRepository.findAllById(fieldIds);

    String employerName = employerRepository.findById(job.getEmployerId())
      .map(Employer::getName)
      .orElse(null);

    jobDtoOut.setProvinces(province);
    jobDtoOut.setFields(field);
    jobDtoOut.setEmployerName(employerName);

    return jobDtoOut;
  }
}
