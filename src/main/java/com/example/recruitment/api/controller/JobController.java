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

@Tag(name = "Job", description = "Quản lý job")
@SecurityRequirement(name = "Authorization")
@RestController
@RequestMapping("/job")
public class JobController {

  @Autowired
  private JobService jobService;

  @Operation(summary = "Lấy thông tin job theo id")
  @ApiResponse(responseCode = "200", description = "Thành công")
  @GetMapping("/{id}")
  public ResponseEntity<CommonDtoOut<JobDtoOut>> get(
    @Parameter(description = "ID của job cần lấy thông tin", required = true)
    @PathVariable("id") Integer id) {
    CommonDtoOut<JobDtoOut> dtoOut = CommonDtoOut.success(this.jobService.get(id));
    return ResponseEntity
      .status(HttpStatus.valueOf(dtoOut.getStatusCode()))
      .body(dtoOut);
  }

  @Operation(summary = "Lấy danh sách job")
  @ApiResponse(responseCode = "200", description = "Thành công")
  @GetMapping()
  public ResponseEntity<CommonDtoOut<PageDtoOut<DataJob>>> list(
    @Parameter(description = "Thông tin phân trang")
    @Valid PageJobDtoIn dto) {
    CommonDtoOut<PageDtoOut<DataJob>> dtoOut = CommonDtoOut.success(this.jobService.list(dto));
    return ResponseEntity
      .status(HttpStatus.valueOf(dtoOut.getStatusCode()))
      .body(dtoOut);
  }

  @Operation(summary = "Tạo mới job")
  @ApiResponse(responseCode = "200", description = "Thành công")
  @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  public ResponseEntity<CommonDtoOut<JobDtoOut>> create(@Valid @RequestBody JobDtoIn dto) {
    CommonDtoOut<JobDtoOut> dtoOut = CommonDtoOut.create(this.jobService.create(dto));
    return ResponseEntity
      .status(HttpStatus.valueOf(dtoOut.getStatusCode()))
      .body(dtoOut);
  }

  @Operation(summary = "Cập nhật thông tin job theo id")
  @ApiResponse(responseCode = "200", description = "Thành công")
  @PutMapping("/{id}")
  public ResponseEntity<CommonDtoOut<JobDtoOut>> update(
    @Parameter(description = "ID của job cần cập nhật thông tin", required = true) @PathVariable("id") Integer id,
    @Valid @RequestBody JobDtoIn dto) {
    CommonDtoOut<JobDtoOut> dtoOut = CommonDtoOut.success(jobService.update(id, dto));
    return ResponseEntity
      .status(HttpStatus.valueOf(dtoOut.getStatusCode()))
      .body(dtoOut);
  }

  @Operation(summary = "Xóa job theo id")
  @ApiResponse(responseCode = "200", description = "Thành công")
  @DeleteMapping("/{id}")
  public ResponseEntity<CommonDtoOut<JobDtoOut>> delete(
    @Parameter(description = "ID của job cần xóa", required = true)
    @PathVariable("id") Integer id) {
    CommonDtoOut<JobDtoOut> dtoOut = CommonDtoOut.success(jobService.delete(id));
    return ResponseEntity
      .status(HttpStatus.valueOf(dtoOut.getStatusCode()))
      .body(dtoOut);
  }
}
