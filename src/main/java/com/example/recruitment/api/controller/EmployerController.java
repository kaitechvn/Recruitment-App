package com.example.recruitment.api.controller;

import com.example.recruitment.common.dto.CommonDtoOut;
import com.example.recruitment.api.dto.in.EmployerDtoIn;
import com.example.recruitment.api.dto.in.page.PageEmployerDtoIn;
import com.example.recruitment.api.dto.in.UpdateEmployerDtoIn;
import com.example.recruitment.api.dto.out.pagedata.DataEmployer;
import com.example.recruitment.api.dto.out.EmployerDtoOut;
import com.example.recruitment.common.dto.PageDtoOut;
import com.example.recruitment.api.service.employer.EmployerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Employer", description = "Quản lý employer")
@SecurityRequirement(name = "Authorization")
@RestController
@RequestMapping("/employer")
public class EmployerController {

  @Autowired
  private EmployerService employerService;

  @Operation(summary = "Lấy thông tin user theo id")
  @GetMapping("/{id}")
  public ResponseEntity<CommonDtoOut<EmployerDtoOut>> get(
    @Parameter(description = "Id của employer cần lấy thông tin", required = true)
    @PathVariable("id") Integer id) {
    CommonDtoOut<EmployerDtoOut> dtoOut = CommonDtoOut.success(this.employerService.get(id));
    return ResponseEntity
      .status(HttpStatus.valueOf(dtoOut.getStatusCode()))
      .body(dtoOut);
  }

  @Operation(summary = "Lấy danh sách user")
  @GetMapping()
  public ResponseEntity<CommonDtoOut<PageDtoOut<DataEmployer>>> list(
    @Parameter(description = "Thông tin phân trang")
    @Valid PageEmployerDtoIn pageDto) {
    CommonDtoOut<PageDtoOut<DataEmployer>> dtoOut = CommonDtoOut.success(this.employerService.list(pageDto));
    return ResponseEntity
      .status(HttpStatus.valueOf(dtoOut.getStatusCode()))
      .body(dtoOut);
  }

  @Operation(summary = "Thêm mới một user")
  @PostMapping()
  @ApiResponse(responseCode = "201", description = "Thành công")
  public ResponseEntity<CommonDtoOut<EmployerDtoOut>> create(
    @Parameter(description = "Thông tin employer mới cần tạo", required = true)
    @Valid @RequestBody EmployerDtoIn dto) {
    CommonDtoOut<EmployerDtoOut> dtoOut = CommonDtoOut.create(this.employerService.create(dto));
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(dtoOut);
  }

  @Operation(summary = "Cập nhật user theo id")
  @PutMapping("/{id}")
  public ResponseEntity<CommonDtoOut<EmployerDtoOut>> update(
    @Parameter(description = "ID của employer cần cập nhật thông tin", required = true) @PathVariable("id") Integer id,
    @Valid @RequestBody UpdateEmployerDtoIn dto) {
    CommonDtoOut<EmployerDtoOut> dtoOut = CommonDtoOut.success(this.employerService.update(id, dto));
    return ResponseEntity
      .status(HttpStatus.valueOf(dtoOut.getStatusCode()))
      .body(dtoOut);
  }

  @Operation(summary = "Xóa user theo id")
  @DeleteMapping("/{id}")
  public ResponseEntity<CommonDtoOut<EmployerDtoOut>> delete(
    @Parameter(description = "ID của employer cần xóa", required = true)
    @PathVariable("id") Integer id) {
    CommonDtoOut<EmployerDtoOut> dtoOut = CommonDtoOut.success(this.employerService.delete(id));
    return ResponseEntity
      .status(HttpStatus.valueOf(dtoOut.getStatusCode()))
      .body(dtoOut);
  }
}
