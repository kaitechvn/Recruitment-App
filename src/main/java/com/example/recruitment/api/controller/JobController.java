package com.example.recruitment.api.controller;

import com.example.recruitment.common.dto.CommonDtoOut;
import com.example.recruitment.common.dto.PageDtoOut;
import com.example.recruitment.api.dto.in.JobDtoIn;
import com.example.recruitment.api.dto.in.page.PageJobDtoIn;
import com.example.recruitment.api.dto.out.pagedata.DataJob;
import com.example.recruitment.api.dto.out.JobDtoOut;
import com.example.recruitment.api.service.job.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Job", description = "Manage jobs")
@SecurityRequirement(name = "Authorization")
@RestController
@RequestMapping("/job")
public class JobController {

  @Autowired
  private JobService jobService;

  @Operation(summary = "Get job information by ID")
  @ApiResponse(responseCode = "200", description = "Success")
  @GetMapping("/{id}")
  public ResponseEntity<CommonDtoOut<JobDtoOut>> get(
    @Parameter(description = "ID of the job to retrieve information for", required = true)
    @PathVariable("id") Integer id) {
    CommonDtoOut<JobDtoOut> dtoOut = CommonDtoOut.success(this.jobService.get(id));
    return ResponseEntity
      .status(HttpStatus.valueOf(dtoOut.getStatusCode()))
      .body(dtoOut);
  }

  @Operation(summary = "Get list of jobs")
  @ApiResponse(responseCode = "200", description = "Success")
  @GetMapping()
  public ResponseEntity<CommonDtoOut<PageDtoOut<DataJob>>> list(
    @Parameter(description = "Pagination information")
    @Valid PageJobDtoIn dto) {
    CommonDtoOut<PageDtoOut<DataJob>> dtoOut = CommonDtoOut.success(this.jobService.list(dto));
    return ResponseEntity
      .status(HttpStatus.valueOf(dtoOut.getStatusCode()))
      .body(dtoOut);
  }

  @Operation(summary = "Create a new job")
  @ApiResponse(responseCode = "200", description = "Success")
  @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  public ResponseEntity<CommonDtoOut<JobDtoOut>> create(@Valid @RequestBody JobDtoIn dto) {
    CommonDtoOut<JobDtoOut> dtoOut = CommonDtoOut.create(this.jobService.create(dto));
    return ResponseEntity
      .status(HttpStatus.valueOf(dtoOut.getStatusCode()))
      .body(dtoOut);
  }

  @Operation(summary = "Update job information by ID")
  @ApiResponse(responseCode = "200", description = "Success")
  @PutMapping("/{id}")
  public ResponseEntity<CommonDtoOut<JobDtoOut>> update(
    @Parameter(description = "ID of the job to update information for", required = true) @PathVariable("id") Integer id,
    @Valid @RequestBody JobDtoIn dto) {
    CommonDtoOut<JobDtoOut> dtoOut = CommonDtoOut.success(jobService.update(id, dto));
    return ResponseEntity
      .status(HttpStatus.valueOf(dtoOut.getStatusCode()))
      .body(dtoOut);
  }

  @Operation(summary = "Delete job by ID")
  @ApiResponse(responseCode = "200", description = "Success")
  @DeleteMapping("/{id}")
  public ResponseEntity<CommonDtoOut<JobDtoOut>> delete(
    @Parameter(description = "ID of the job to delete", required = true)
    @PathVariable("id") Integer id) {
    CommonDtoOut<JobDtoOut> dtoOut = CommonDtoOut.success(jobService.delete(id));
    return ResponseEntity
      .status(HttpStatus.valueOf(dtoOut.getStatusCode()))
      .body(dtoOut);
  }
}
