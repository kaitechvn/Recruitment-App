package com.example.recruitment.api.controller;

import com.example.recruitment.common.dto.CommonDtoOut;
import com.example.recruitment.common.dto.PageDtoOut;
import com.example.recruitment.api.dto.in.page.PageResumeDtoIn;
import com.example.recruitment.api.dto.in.ResumeDtoIn;
import com.example.recruitment.api.dto.in.UpdateResumeDtoIn;
import com.example.recruitment.api.dto.out.pagedata.DataResume;
import com.example.recruitment.api.dto.out.ResumeDtoOut;
import com.example.recruitment.api.service.resume.ResumeService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Resume", description = "Quản lý resume")
@SecurityRequirement(name = "Authorization")
@RestController
@RequestMapping("/resume")
public class ResumeController {

  @Autowired
  private ResumeService resumeService;

  @GetMapping("{id}")
  public ResponseEntity<CommonDtoOut<ResumeDtoOut>> get(@PathVariable("id") Integer id) {
    CommonDtoOut<ResumeDtoOut> dtoOut = CommonDtoOut.success(resumeService.get(id));
    return new ResponseEntity<>(
      dtoOut, HttpStatusCode.valueOf(dtoOut.getStatusCode())
    );
  }

  @PostMapping
  public ResponseEntity<CommonDtoOut<ResumeDtoOut>> create(@RequestBody ResumeDtoIn dtoIn) {
    CommonDtoOut<ResumeDtoOut> dtoOut = CommonDtoOut.create(resumeService.create(dtoIn));
    return new ResponseEntity<>(dtoOut, HttpStatusCode.valueOf(dtoOut.getStatusCode()));
  }

  @PutMapping("{id}")
  public ResponseEntity<CommonDtoOut<ResumeDtoOut>> update(@PathVariable("id") Integer id, @RequestBody UpdateResumeDtoIn dtoIn) {
    CommonDtoOut<ResumeDtoOut> dtoOut = CommonDtoOut.success(resumeService.update(id, dtoIn));
    return new ResponseEntity<>(dtoOut, HttpStatusCode.valueOf(dtoOut.getStatusCode()));
  }

  @DeleteMapping("{id}")
  public ResponseEntity<CommonDtoOut<ResumeDtoOut>> delete(@PathVariable("id") Integer id) {
    CommonDtoOut<ResumeDtoOut> dtoOut = CommonDtoOut.success(resumeService.delete(id));
    return new ResponseEntity<>(dtoOut, HttpStatusCode.valueOf(dtoOut.getStatusCode()));
  }

  @GetMapping()
  public ResponseEntity<CommonDtoOut<PageDtoOut<DataResume>>> list(@Valid PageResumeDtoIn dto){
    CommonDtoOut<PageDtoOut<DataResume>> dtoOut = CommonDtoOut.success(this.resumeService.list(dto));
    return new ResponseEntity<>(
      dtoOut, HttpStatusCode.valueOf(dtoOut.getStatusCode())
    );
  }
}