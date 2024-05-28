package com.example.recruitment.service;

import com.example.recruitment.common.data_transform.Update;
import com.example.recruitment.common.dto.CommonDtoOut;
import com.example.recruitment.common.dto.PageDtoOut;
import com.example.recruitment.common.error.ErrorCode;
import com.example.recruitment.common.error.ServiceValid;
import com.example.recruitment.common.exception.ApiException;
import com.example.recruitment.dto.in.page.PageResumeDtoIn;
import com.example.recruitment.dto.in.ResumeDtoIn;
import com.example.recruitment.dto.in.UpdateResumeDtoIn;
import com.example.recruitment.dto.out.pagedata.DataResume;
import com.example.recruitment.dto.out.ResumeDtoOut;
import com.example.recruitment.entity.Resume;
import com.example.recruitment.repository.FieldRepository;
import com.example.recruitment.repository.ProvinceRepository;
import com.example.recruitment.repository.ResumeRepository;
import com.example.recruitment.repository.SeekerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResumeServiceImpl implements ResumeService {

  @Autowired
  private ResumeRepository resumeRepository;

  @Autowired
  private ProvinceRepository provinceRepository;

  @Autowired
  private FieldRepository fieldRepository;

  @Autowired
  private SeekerRepository seekerRepository;

  @Override
  public CommonDtoOut<ResumeDtoOut> get(Integer id) {
    Resume resume= this.resumeRepository.findById(id)
      .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "resume not found"));

    return CommonDtoOut.success(ResumeDtoOut.fromResume(resume));

  }

  @Override
  public CommonDtoOut<ResumeDtoOut> create(ResumeDtoIn dto) {
    String errorMessage = ServiceValid.validateIds(
      dto.getProvinceIds(),
      dto.getFieldIds(),
      dto.getSeekerId(),
      provinceRepository,
      fieldRepository,
      seekerRepository::findById,
      "Seeker"
    );

    if (!errorMessage.isEmpty()) {
      throw new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, errorMessage);
    } else {
      Resume newResume = this.resumeRepository.save(Resume.fromDto(dto));
      ResumeDtoOut resumeOut = ResumeDtoOut.fromResume(newResume);
      return CommonDtoOut.create(resumeOut);
    }
  }

  @Override
  public CommonDtoOut<ResumeDtoOut> update(Integer id, UpdateResumeDtoIn dto) {
    Resume updatingResume= this.resumeRepository.findById(id)
      .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "resume not found"));

    String errorMessage = ServiceValid.validateIds(
      dto.getProvinceIds(),
      dto.getFieldIds(),
      updatingResume.getSeekerId(),
      provinceRepository,
      fieldRepository,
      seekerRepository::findById,
      "Seeker"
    );

    if (!errorMessage.isEmpty()) {
      throw new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, errorMessage);
    } else {

      Update.copyIgnoreNull(dto, updatingResume); // Not null field update
      Resume updatedResume = this.resumeRepository.save(updatingResume);
      ResumeDtoOut resumeOut = ResumeDtoOut.fromResume(updatedResume);

      if (dto.getProvinceIds() != null) {
        resumeOut.setProvinces(this.provinceRepository.findAllById(dto.getProvinceIds()));
      }

      if (dto.getFieldIds() != null ) {
        resumeOut.setFields(this.fieldRepository.findAllById(dto.getFieldIds()));
      }

      return CommonDtoOut.success(resumeOut);
    }
  }

  @Override
  public CommonDtoOut<ResumeDtoOut> delete(Integer id) {
    Resume resume= this.resumeRepository.findById(id)
      .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "resume not found"));

    ResumeDtoOut resumeOut = ResumeDtoOut.fromResume(resume);
    this.resumeRepository.delete(resume);
    return CommonDtoOut.success(resumeOut);
  }

  @Override
  public CommonDtoOut<PageDtoOut<DataResume>> list(PageResumeDtoIn dto) {
    Pageable paging = PageRequest.of(dto.getPage() - 1, dto.getPageSize(),
      Sort.by("title").ascending());

    Page<Resume> pagedResult = dto.getSeekerId() == -1 ?
      resumeRepository.findAll(paging) :
      resumeRepository.findAllBySeekerId(dto.getSeekerId(), paging);

    List<DataResume> dataResumeList = pagedResult.getContent().stream()
      .map(DataResume::fromResume).sorted(Comparator
        .comparing(DataResume::getTitle, Comparator.nullsLast(Comparator.naturalOrder()))
        .thenComparing(DataResume::getSeekerName, Comparator.nullsLast(Comparator.naturalOrder()))).collect(Collectors.toList());

    return CommonDtoOut.success(
      PageDtoOut.from(dto.getPage(), dto.getPageSize(), pagedResult.getTotalElements(),
        dataResumeList)
    );
  }
}
