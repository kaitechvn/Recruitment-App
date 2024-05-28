package com.example.recruitment.service;

import com.example.recruitment.common.data_transform.Update;
import com.example.recruitment.common.dto.CommonDtoOut;
import com.example.recruitment.common.dto.PageDtoOut;

import com.example.recruitment.common.error.ErrorCode;
import com.example.recruitment.common.exception.ApiException;
import com.example.recruitment.dto.in.page.PageSeekerDtoIn;
import com.example.recruitment.dto.in.SeekerDtoIn;
import com.example.recruitment.dto.out.pagedata.DataSeeker;
import com.example.recruitment.dto.out.SeekerDtoOut;
import com.example.recruitment.entity.Seeker;
import com.example.recruitment.repository.ProvinceRepository;
import com.example.recruitment.repository.SeekerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class SeekerServiceImpl implements SeekerService{

  @Autowired
  private SeekerRepository seekerRepository;

  @Autowired
  private ProvinceRepository provinceRepository;

  @Override
  public CommonDtoOut<SeekerDtoOut> get(Integer id) {
    Seeker seeker = this.seekerRepository.findById(id)
      .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "seeker not found"));

    return CommonDtoOut.success(SeekerDtoOut.fromSeeker(seeker));

  }

  @Override
  public CommonDtoOut<SeekerDtoOut> create(SeekerDtoIn dto) {
    this.provinceRepository.findById(dto.getProvince())
      .orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "province not found"));

    Seeker addSeeker = this.seekerRepository.save(Seeker.fromDto(dto));
    SeekerDtoOut seekerOut = SeekerDtoOut.fromSeeker(addSeeker);
    return CommonDtoOut.create(seekerOut);

  }

  @Override
  public CommonDtoOut<SeekerDtoOut> update(Integer id, SeekerDtoIn dto) {
    Seeker updatingSeeker = this.seekerRepository.findById(id)
      .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "seeker not found"));

    this.provinceRepository.findById(dto.getProvince())
      .orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "province not found"));

    Update.copyIgnoreNull(dto, updatingSeeker); // not null field update
    Seeker toUpdateSeeker = this.seekerRepository.save(updatingSeeker);
    SeekerDtoOut seekerOut = SeekerDtoOut.fromSeeker(toUpdateSeeker);
    return CommonDtoOut.success(seekerOut);

    }

  @Override
  public CommonDtoOut<SeekerDtoOut> delete(Integer id) {
    Seeker seeker = this.seekerRepository.findById(id)
      .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "seeker not found"));

    SeekerDtoOut seekerOut = SeekerDtoOut.fromSeeker(seeker);
    this.seekerRepository.delete(seeker);
    return CommonDtoOut.success(seekerOut);

  }

  @Override
  public CommonDtoOut<PageDtoOut<DataSeeker>> list(PageSeekerDtoIn dto) {
      Pageable paging = PageRequest.of(dto.getPage() - 1, dto.getPageSize(), Sort.by("name").ascending());
      Page<Seeker> pagedResult;
      if (dto.getProvinceId() == -1) {
        pagedResult = this.seekerRepository.findAll(paging);
      } else {
        pagedResult = this.seekerRepository.findAllByProvince(dto.getProvinceId(), paging);
      }

      return CommonDtoOut.success(
        PageDtoOut.from(dto.getPage(), dto.getPageSize(), pagedResult.getTotalElements(),
          pagedResult.stream().map(DataSeeker::fromSeeker).toList())
      );
    }
  }
