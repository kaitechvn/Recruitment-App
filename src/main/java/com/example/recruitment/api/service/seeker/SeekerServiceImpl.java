package com.example.recruitment.api.service.seeker;

import com.example.recruitment.api.dto.in.UpdateSeekerDto;
import com.example.recruitment.api.entity.Province;
import com.example.recruitment.api.mapper.SeekerMapper;
import com.example.recruitment.common.dto.PageDtoOut;
import com.example.recruitment.common.code.ErrorCode;
import com.example.recruitment.common.exception.ApiException;
import com.example.recruitment.api.dto.in.page.PageSeekerDtoIn;
import com.example.recruitment.api.dto.in.SeekerDtoIn;
import com.example.recruitment.api.dto.out.pagedata.DataSeeker;
import com.example.recruitment.api.dto.out.SeekerDtoOut;
import com.example.recruitment.api.entity.Seeker;
import com.example.recruitment.api.repository.ProvinceRepository;
import com.example.recruitment.api.repository.SeekerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class SeekerServiceImpl implements SeekerService{

  private final SeekerRepository seekerRepository;
  private final ProvinceRepository provinceRepository;
  private final SeekerMapper seekerMapper = SeekerMapper.INSTANCE;

  @Autowired
  public SeekerServiceImpl(SeekerRepository seekerRepository,
                           ProvinceRepository provinceRepository) {
    this.seekerRepository = seekerRepository;
    this.provinceRepository = provinceRepository;
  }

  @Override
  public SeekerDtoOut get(Integer id) {
    Seeker seeker = this.seekerRepository.findById(id)
      .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "seeker not found"));

    return mapSeekerToDto(seeker);

  }

  @Override
  public SeekerDtoOut create(SeekerDtoIn dto) {
    this.provinceRepository.findById(dto.getProvinceId())
      .orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST, HttpStatus.BAD_REQUEST, "province not found"));

    Seeker addSeeker = this.seekerRepository.save(seekerMapper.toSeeker(dto));

    return mapSeekerToDto(addSeeker);

  }

  @Override
  public SeekerDtoOut update(Integer id, UpdateSeekerDto dto) {
    Seeker existingSeeker = seekerRepository.findById(id)
      .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "employer not found"));

    seekerMapper.toUpdateSeeker(dto, existingSeeker);

    Seeker updatedSeeker = seekerRepository.save(existingSeeker);

    return mapSeekerToDto(updatedSeeker);

    }

  @Override
  public SeekerDtoOut delete(Integer id) {
    Seeker seeker = this.seekerRepository.findById(id)
      .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "seeker not found"));

    this.seekerRepository.delete(seeker);
    return mapSeekerToDto(seeker);

  }

  @Override
  public PageDtoOut<DataSeeker> list(PageSeekerDtoIn dto) {
      Pageable paging = PageRequest.of(dto.getPage() - 1, dto.getPageSize(), Sort.by("name").ascending());
      Page<Seeker> pagedResult;
      if (dto.getProvinceId() == -1) {
        pagedResult = this.seekerRepository.findAll(paging);
      } else {
        pagedResult = this.seekerRepository.findAllByProvince(dto.getProvinceId(), paging);
      }

      return PageDtoOut.from(dto.getPage(), dto.getPageSize(), pagedResult.getTotalElements(),
          pagedResult.stream().map(seeker -> seekerMapper.toDataSeeker(seeker)).toList());
    }

    public SeekerDtoOut mapSeekerToDto(Seeker seeker) {
    SeekerDtoOut seekerDtoOut = seekerMapper.toSeekerDtoOut(seeker);

      String provinceName = provinceRepository.findById(seeker.getProvince())
        .map(Province::getName)
        .orElse(null);

      seekerDtoOut.setProvinceName(provinceName);

      return seekerDtoOut;
    }

  }
