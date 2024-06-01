package com.example.recruitment.service;

import com.example.recruitment.common.data_transform.Update;
import com.example.recruitment.common.dto.CommonDtoOut;
import com.example.recruitment.common.error.ErrorCode;
import com.example.recruitment.common.exception.ApiException;
import com.example.recruitment.dto.in.EmployerDtoIn;
import com.example.recruitment.dto.in.page.PageEmployerDtoIn;
import com.example.recruitment.dto.in.UpdateEmployerDtoIn;
import com.example.recruitment.dto.out.pagedata.DataEmployer;
import com.example.recruitment.dto.out.EmployerDtoOut;
import com.example.recruitment.common.dto.PageDtoOut;
import com.example.recruitment.entity.Employer;
import com.example.recruitment.repository.EmployerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
public class EmployerServiceImpl implements EmployerService {

    @Autowired
    private EmployerRepository employerRepository;

    @Cacheable(value = "employers", key = "#id")
    @Override
    public CommonDtoOut<EmployerDtoOut> get(Integer id) {
      Employer employer = this.employerRepository.findById(id)
        .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "employer not found"));
//        System.out.println("Method invoked to fetch employer with id: " + id);
        return CommonDtoOut.success(EmployerDtoOut.fromEmployer(employer));
    }

    @Override
    public CommonDtoOut<EmployerDtoOut> create(EmployerDtoIn dto) {
        this.employerRepository.findByEmail(dto.getEmail()).ifPresent(check -> {
        throw new ApiException(ErrorCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "email already existed");
      });

        Employer addEmployer = this.employerRepository.save(Employer.fromDto(dto));
        EmployerDtoOut toDtoOut = EmployerDtoOut.fromEmployer(addEmployer);
        return CommonDtoOut.create(toDtoOut);
    }

    @CachePut(value = "employers", key = "#id")
    @Override
    public CommonDtoOut<EmployerDtoOut> update(Integer id, UpdateEmployerDtoIn dto) {
      Employer updatingEmployer = this.employerRepository.findById(id)
        .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "employer not found"));

      Update.copyIgnoreNull(dto, updatingEmployer); // not null field update
      Employer toUpdateEmployer = this.employerRepository.save(updatingEmployer);
      EmployerDtoOut toDtoOut = EmployerDtoOut.fromEmployer(toUpdateEmployer);
      return CommonDtoOut.success(toDtoOut);

    }

    @CacheEvict(value = "employers", key = "#id")
    @Override
    public CommonDtoOut<EmployerDtoOut> delete(Integer id) {
      Employer employer = this.employerRepository.findById(id)
        .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "employer not found"));

      EmployerDtoOut toDtoOut = EmployerDtoOut.fromEmployer(employer);
      this.employerRepository.delete(employer);
      return CommonDtoOut.success(toDtoOut);
    }

    @Override
    public CommonDtoOut<PageDtoOut<DataEmployer>> list(PageEmployerDtoIn dto) {
        Pageable paging = PageRequest.of(dto.getPage() - 1, dto.getPageSize(), Sort.by("name").ascending());
        Page<Employer> pagedResult = this.employerRepository.findAll(paging);
        return CommonDtoOut.success(
          PageDtoOut.from(dto.getPage(), dto.getPageSize(), pagedResult.getTotalElements(),
                        pagedResult.stream().map(DataEmployer::fromEmployer).toList()));
        }
    }

