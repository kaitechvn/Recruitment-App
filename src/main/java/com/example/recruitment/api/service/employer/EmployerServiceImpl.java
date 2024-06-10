package com.example.recruitment.api.service.employer;

import com.example.recruitment.common.code.ErrorCode;
import com.example.recruitment.common.exception.ApiException;
import com.example.recruitment.api.dto.in.EmployerDtoIn;
import com.example.recruitment.api.dto.in.page.PageEmployerDtoIn;
import com.example.recruitment.api.dto.in.UpdateEmployerDtoIn;
import com.example.recruitment.api.dto.out.pagedata.DataEmployer;
import com.example.recruitment.api.dto.out.EmployerDtoOut;
import com.example.recruitment.common.dto.PageDtoOut;
import com.example.recruitment.api.entity.Employer;
import com.example.recruitment.api.mapper.Mapper;
import com.example.recruitment.api.repository.EmployerRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public EmployerDtoOut get(Integer id) {
      Employer employer = this.employerRepository.findById(id)
        .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "employer not found"));

      //        System.out.println("Method invoked to fetch employer with id: " + id);
        return EmployerDtoOut.fromEmployer(employer);
    }

    @Override
    public EmployerDtoOut create(EmployerDtoIn dto) {
      this.employerRepository.findByEmail(dto.getEmail()).ifPresent(check -> {
        throw new ApiException(ErrorCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "email already existed");
      });

      Employer addEmployer = this.employerRepository.save(Employer.fromDto(dto));
      return EmployerDtoOut.fromEmployer(addEmployer);
    }

    @Override
    public EmployerDtoOut update(Integer id, UpdateEmployerDtoIn dto) {
      Employer updatingEmployer = this.employerRepository.findById(id)
        .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "employer not found"));

      Mapper.UpdateNonNull(dto, updatingEmployer);

      Employer toUpdateEmployer = this.employerRepository.save(updatingEmployer);
      return EmployerDtoOut.fromEmployer(toUpdateEmployer);
    }

    @Override
    public EmployerDtoOut delete(Integer id) {
      Employer employer = this.employerRepository.findById(id)
        .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "employer not found"));

      EmployerDtoOut employerDtoOut = EmployerDtoOut.fromEmployer(employer);
      this.employerRepository.deleteById(id);
      return employerDtoOut;
    }

    @Override
    public PageDtoOut<DataEmployer> list(PageEmployerDtoIn dto) {
        Pageable paging = PageRequest.of(dto.getPage() - 1, dto.getPageSize(), Sort.by("name").ascending());
        Page<Employer> pagedResult = this.employerRepository.findAll(paging);
        return PageDtoOut.from(dto.getPage(), dto.getPageSize(), pagedResult.getTotalElements(),
                        pagedResult.stream().map(DataEmployer::fromEmployer).toList());
        }
    }

