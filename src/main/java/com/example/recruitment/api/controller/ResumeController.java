package com.example.recruitment.api.controller;

import com.example.recruitment.common.dto.CommonDtoOut;
import com.example.recruitment.common.dto.PageDtoOut;
import com.example.recruitment.api.dto.in.page.PageResumeDtoIn;
import com.example.recruitment.api.dto.in.ResumeDtoIn;
import com.example.recruitment.api.dto.in.UpdateResumeDtoIn;
import com.example.recruitment.api.dto.out.pagedata.DataResume;
import com.example.recruitment.api.dto.out.ResumeDtoOut;
import com.example.recruitment.api.service.resume.ResumeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Resume", description = "Manage resumes")
@SecurityRequirement(name = "Authorization")
@RestController
@RequestMapping("/resume")
public class ResumeController {

  @Autowired
  private ResumeService resumeService;

  @Operation(summary = "Get resume information by ID")
  @ApiResponse(responseCode = "200", description = "Success")
  @GetMapping("{id}")
  public ResponseEntity<CommonDtoOut<ResumeDtoOut>> get(
    @Parameter(description = "ID of the resume to retrieve information for", required = true)
    @PathVariable("id") Integer id) {
    CommonDtoOut<ResumeDtoOut> dtoOut = CommonDtoOut.success(resumeService.get(id));
    return new ResponseEntity<>(
      dtoOut, HttpStatus.valueOf(dtoOut.getStatusCode())
    );
  }

  @Operation(summary = "Create a new resume")
  @ApiResponse(responseCode = "201", description = "Created")
  @PostMapping
  public ResponseEntity<CommonDtoOut<ResumeDtoOut>> create(
    @Parameter(description = "Information of the new resume to be created", required = true)
    @RequestBody ResumeDtoIn dtoIn) {
    CommonDtoOut<ResumeDtoOut> dtoOut = CommonDtoOut.create(resumeService.create(dtoIn));
    return new ResponseEntity<>(dtoOut, HttpStatus.valueOf(dtoOut.getStatusCode()));
  }

  @Operation(summary = "Update resume information by ID")
  @ApiResponse(responseCode = "200", description = "Success")
  @PutMapping("{id}")
  public ResponseEntity<CommonDtoOut<ResumeDtoOut>> update(
    @Parameter(description = "ID of the resume to update information for", required = true) @PathVariable("id") Integer id,
    @Parameter(description = "Updated information of the resume", required = true) @RequestBody UpdateResumeDtoIn dtoIn) {
    CommonDtoOut<ResumeDtoOut> dtoOut = CommonDtoOut.success(resumeService.update(id, dtoIn));
    return new ResponseEntity<>(dtoOut, HttpStatus.valueOf(dtoOut.getStatusCode()));
  }

  @Operation(summary = "Delete resume by ID")
  @ApiResponse(responseCode = "200", description = "Success")
  @DeleteMapping("{id}")
  public ResponseEntity<CommonDtoOut<ResumeDtoOut>> delete(
    @Parameter(description = "ID of the resume to delete", required = true) @PathVariable("id") Integer id) {
    CommonDtoOut<ResumeDtoOut> dtoOut = CommonDtoOut.success(resumeService.delete(id));
    return new ResponseEntity<>(dtoOut, HttpStatus.valueOf(dtoOut.getStatusCode()));
  }

  @Operation(summary = "Get list of resumes")
  @ApiResponse(responseCode = "200", description = "Success")
  @GetMapping()
  public ResponseEntity<CommonDtoOut<PageDtoOut<DataResume>>> list(
    @Parameter(description = "Pagination information") @Valid PageResumeDtoIn dto) {
    CommonDtoOut<PageDtoOut<DataResume>> dtoOut = CommonDtoOut.success(this.resumeService.list(dto));
    return new ResponseEntity<>(
      dtoOut, HttpStatus.valueOf(dtoOut.getStatusCode())
    );
  }
}
