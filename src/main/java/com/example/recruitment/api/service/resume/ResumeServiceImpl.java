package com.example.recruitment.api.service.resume;

import com.example.recruitment.api.entity.*;
import com.example.recruitment.common.data_transform.Converter;
import com.example.recruitment.common.dto.PageDtoOut;
import com.example.recruitment.common.code.ErrorCode;
import com.example.recruitment.common.exception.ApiException;
import com.example.recruitment.api.dto.in.page.PageResumeDtoIn;
import com.example.recruitment.api.dto.in.ResumeDtoIn;
import com.example.recruitment.api.dto.out.pagedata.DataResume;
import com.example.recruitment.api.dto.out.ResumeDtoOut;
import com.example.recruitment.api.mapper.ResumeMapper;
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

  private final ResumeRepository resumeRepository;
  private final ProvinceRepository provinceRepository;
  private final FieldRepository fieldRepository;
  private final SeekerRepository seekerRepository;
  private final ResumeMapper resumeMapper = ResumeMapper.INSTANCE;

  @Autowired
  public ResumeServiceImpl(ResumeRepository resumeRepository, ProvinceRepository provinceRepository, FieldRepository fieldRepository, SeekerRepository seekerRepository) {
    this.resumeRepository = resumeRepository;
    this.provinceRepository = provinceRepository;
    this.fieldRepository = fieldRepository;
    this.seekerRepository = seekerRepository;
  }

  @Override
  public ResumeDtoOut get(Integer id) {
    Resume resume = this.resumeRepository.findById(id)
      .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "resume not found"));

    return mapResumeDtoOut(resume);
  }

  @Override
  public ResumeDtoOut create(ResumeDtoIn dto) {
    validateDto(dto.getProvinceIds(), dto.getFieldIds(), dto.getSeekerId());
    Resume newResume = this.resumeRepository.save(resumeMapper.toResume(dto));
    return mapResumeDtoOut(newResume);
  }

  @Override
  public ResumeDtoOut update(Integer id, ResumeDtoIn dto) {
    Resume updatingResume = this.resumeRepository.findById(id)
      .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "resume not found"));

    if (!dto.getProvinceIds().isEmpty()) {
      validateDto(dto.getProvinceIds(), dto.getFieldIds(), dto.getSeekerId());
    }

    Resume updatedResume = this.resumeRepository.save(updatingResume);

    return mapResumeDtoOut(updatedResume);
  }

  @Override
  public ResumeDtoOut delete(Integer id) {
    Resume resume = this.resumeRepository.findById(id)
      .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "resume not found"));

    this.resumeRepository.delete(resume);
    return mapResumeDtoOut(resume);
  }

  @Override
  public PageDtoOut<DataResume> list(PageResumeDtoIn dto) {
    Pageable paging = PageRequest.of(dto.getPage() - 1, dto.getPageSize(),
      Sort.by("title").ascending());

    Page<Resume> pagedResult = dto.getSeekerId() == -1 ?
      resumeRepository.findAll(paging) :
      resumeRepository.findAllBySeekerId(dto.getSeekerId(), paging);

    List<DataResume> dataResumeList = pagedResult.getContent().stream()
      .map(resumeMapper::toDataResume)
      .sorted(Comparator
        .comparing(DataResume::getTitle, Comparator.nullsLast(Comparator.naturalOrder()))
        .thenComparing(DataResume::getSeekerName, Comparator.nullsLast(Comparator.naturalOrder())))
      .collect(Collectors.toList());

    return PageDtoOut.from(dto.getPage(), dto.getPageSize(), pagedResult.getTotalElements(), dataResumeList);
  }

  private void validateDto(List<Integer> provinceIds, List<Integer> fieldIds, Integer seekerId) {
    for (Integer id : provinceIds) {
      if (!provinceRepository.existsById(id)) {
        throw new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "province ID " + id + " not found");
      }
    }

    for (Integer id : fieldIds) {
      if (!fieldRepository.existsById(id)) {
        throw new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "field ID " + id + " not found");
      }
    }

    if (seekerId != null && !seekerRepository.existsById(seekerId)) {
      throw new ApiException(ErrorCode.NOT_FOUND, HttpStatus.NOT_FOUND, "seeker not found");
    }
  }

  private ResumeDtoOut mapResumeDtoOut(Resume resume) {

    ResumeDtoOut resumeDtoOut = resumeMapper.toResumeDtoOut(resume);

    List<Integer> provinceIds = Converter.extractIdFromStringDb(resume.getProvinces());
    List<Province> province = provinceRepository.findAllById(provinceIds);

    List<Integer> fieldIds = Converter.extractIdFromStringDb(resume.getFields());
    List<Field> field = fieldRepository.findAllById(fieldIds);

    String seekerName = seekerRepository.findById(resume.getSeekerId())
      .map(Seeker::getName)
      .orElse(null);

    resumeDtoOut.setProvinces(province);
    resumeDtoOut.setFields(field);
    resumeDtoOut.setSeekerName(seekerName);

    return resumeDtoOut;
  }
}
