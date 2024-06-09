package com.example.recruitment.api.service.resume;

import com.example.recruitment.common.dto.PageDtoOut;
import com.example.recruitment.common.code.ErrorCode;
import com.example.recruitment.common.exception.ApiException;
import com.example.recruitment.api.dto.in.page.PageResumeDtoIn;
import com.example.recruitment.api.dto.in.ResumeDtoIn;
import com.example.recruitment.api.dto.in.UpdateResumeDtoIn;
import com.example.recruitment.api.dto.out.pagedata.DataResume;
import com.example.recruitment.api.dto.out.ResumeDtoOut;
import com.example.recruitment.api.entity.Resume;
import com.example.recruitment.api.mapper.Mapper;
import com.example.recruitment.api.repository.FieldRepository;
import com.example.recruitment.api.repository.ProvinceRepository;
import com.example.recruitment.api.repository.ResumeRepository;
import com.example.recruitment.api.repository.SeekerRepository;
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
  public ResumeDtoOut get(Integer id) {
    Resume resume= this.resumeRepository.findById(id)
      .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "resume not found"));

    return ResumeDtoOut.fromResume(resume);
  }

  @Override
  public ResumeDtoOut create(ResumeDtoIn dto) {

    for (Integer id : dto.getProvinceIds()) {
      if (!provinceRepository.existsById(id)) {
        throw new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "province ID " + id + " not found");
      }
    }

    for (Integer id : dto.getFieldIds()) {
      if (!fieldRepository.existsById(id)) {
        throw new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "field ID " + id + " not found");
      }
    }

    if (!seekerRepository.existsById(dto.getSeekerId())) {
      throw new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "seeker not found");
    }

    Resume newResume = this.resumeRepository.save(Resume.fromDto(dto));
    return ResumeDtoOut.fromResume(newResume);
    }


  @Override
  public ResumeDtoOut update(Integer id, UpdateResumeDtoIn dto) {
    Resume updatingResume= this.resumeRepository.findById(id)
      .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "resume not found"));

    if(!dto.getProvinceIds().isEmpty()) {
      for (Integer pid : dto.getProvinceIds()) {
        if (!provinceRepository.existsById(id)) {
          throw new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "province ID " + pid + " not found");
        }
      }
    }

    if(!dto.getFieldIds().isEmpty()) {
      for (Integer fid : dto.getFieldIds()) {
        if (!fieldRepository.existsById(id)) {
          throw new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "field ID " + fid + " not found");
        }
      }
    }

    Mapper.UpdateNonNull(dto, updatingResume); // Not null field update
    Resume updatedResume = this.resumeRepository.save(updatingResume);

    return ResumeDtoOut.fromResume(updatedResume);
    }

  @Override
  public ResumeDtoOut delete(Integer id) {
    Resume resume= this.resumeRepository.findById(id)
      .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "resume not found"));

    ResumeDtoOut resumeDtoOut = ResumeDtoOut.fromResume(resume);
    this.resumeRepository.delete(resume);
    return resumeDtoOut;
  }

  @Override
  public PageDtoOut<DataResume> list(PageResumeDtoIn dto) {
    Pageable paging = PageRequest.of(dto.getPage() - 1, dto.getPageSize(),
      Sort.by("title").ascending());

    Page<Resume> pagedResult = dto.getSeekerId() == -1 ?
      resumeRepository.findAll(paging) :
      resumeRepository.findAllBySeekerId(dto.getSeekerId(), paging);

    List<DataResume> dataResumeList = pagedResult.getContent().stream()
      .map(DataResume::fromResume).sorted(Comparator
        .comparing(DataResume::getTitle, Comparator.nullsLast(Comparator.naturalOrder()))
        .thenComparing(DataResume::getSeekerName, Comparator.nullsLast(Comparator.naturalOrder()))).collect(Collectors.toList());

    return PageDtoOut.from(dto.getPage(), dto.getPageSize(), pagedResult.getTotalElements(),
        dataResumeList);
  }
}
