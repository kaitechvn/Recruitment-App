package com.example.recruitment.api.service.employer;

import com.example.recruitment.api.dto.in.update.UpdateEmployerDto;
import com.example.recruitment.api.entity.Province;
import com.example.recruitment.api.mapper.EmployerMapper;
import com.example.recruitment.api.repository.ProvinceRepository;
import com.example.recruitment.common.code.ErrorCode;
import com.example.recruitment.common.exception.ApiException;
import com.example.recruitment.api.dto.in.EmployerDtoIn;
import com.example.recruitment.api.dto.in.page.PageEmployerDtoIn;
import com.example.recruitment.api.dto.out.pagedata.DataEmployer;
import com.example.recruitment.api.dto.out.EmployerDtoOut;
import com.example.recruitment.common.dto.PageDtoOut;
import com.example.recruitment.api.entity.Employer;
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

  private final ProvinceRepository provinceRepository;
  private final EmployerRepository employerRepository;

  @Autowired
  public EmployerServiceImpl(EmployerRepository employerRepository, ProvinceRepository provinceRepository) {
    this.employerRepository = employerRepository;
    this.provinceRepository = provinceRepository;
  }

  private final EmployerMapper employerMapper = EmployerMapper.INSTANCE;

  @Override
    public EmployerDtoOut get(Integer id) {
      Employer employer = this.employerRepository.findById(id)
        .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "employer not found"));

        return mapEmployerDtoOut(employer);
    }

    @Override
    public EmployerDtoOut create(EmployerDtoIn dto) {
      this.employerRepository.findByEmail(dto.getEmail()).ifPresent(check -> {
        throw new ApiException(ErrorCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "email already existed");
      });

      Employer addEmployer = this.employerRepository.save(employerMapper.toEmployer(dto));
      return mapEmployerDtoOut(addEmployer);
    }

    @Override
    public EmployerDtoOut update(Integer id, UpdateEmployerDto dto) {
      Employer existingEmployer = employerRepository.findById(id)
        .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "employer not found"));

      employerMapper.toUpdateEmployer(dto, existingEmployer);

      Employer updatedEmployer = employerRepository.save(existingEmployer);
      return mapEmployerDtoOut(updatedEmployer) ;
    }

    @Override
    public EmployerDtoOut delete(Integer id) {
      Employer employer = this.employerRepository.findById(id)
        .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "employer not found"));

      this.employerRepository.deleteById(id);

      return mapEmployerDtoOut(employer);

    }

    @Override
    public PageDtoOut<DataEmployer> list(PageEmployerDtoIn dto) {
        Pageable paging = PageRequest.of(dto.getPage() - 1, dto.getPageSize(), Sort.by("name").ascending());
        Page<Employer> pagedResult = this.employerRepository.findAll(paging);
        return PageDtoOut.from(dto.getPage(), dto.getPageSize(), pagedResult.getTotalElements(),
                        pagedResult.stream()
                                    .map(employerMapper::toDataEmployer)
                                    .toList());
        }

    private EmployerDtoOut mapEmployerDtoOut(Employer employer) {
      EmployerDtoOut employerDtoOut = employerMapper.toEmployerDtoOut(employer);

      String provinceName = provinceRepository.findById(employerDtoOut.getProvinceId())
        .map(Province::getName)
        .orElse(null);

      employerDtoOut.setProvinceName(provinceName);

      return employerDtoOut;
    }

}

