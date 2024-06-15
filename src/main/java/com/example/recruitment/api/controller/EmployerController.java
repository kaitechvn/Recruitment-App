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
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Employer", description = "Manage employers")
@SecurityRequirement(name = "Authorization")
@RestController
@RequestMapping("/employer")
public class EmployerController {

  @Autowired
  private EmployerService employerService;

  @Operation(summary = "Get employer information by ID")
  @GetMapping("/{id}")
  public ResponseEntity<CommonDtoOut<EmployerDtoOut>> get(
    @Parameter(description = "ID of the employer to retrieve information for", required = true)
    @PathVariable("id") Integer id) {
    CommonDtoOut<EmployerDtoOut> dtoOut = CommonDtoOut.success(this.employerService.get(id));
    return ResponseEntity
      .status(HttpStatus.valueOf(dtoOut.getStatusCode()))
      .body(dtoOut);
  }

  @Operation(summary = "Get list of employers")
  @GetMapping()
  public ResponseEntity<CommonDtoOut<PageDtoOut<DataEmployer>>> list(
    @Parameter(description = "Pagination information")
    @Valid PageEmployerDtoIn pageDto) {
    CommonDtoOut<PageDtoOut<DataEmployer>> dtoOut = CommonDtoOut.success(this.employerService.list(pageDto));
    return ResponseEntity
      .status(HttpStatus.valueOf(dtoOut.getStatusCode()))
      .body(dtoOut);
  }

  @Operation(summary = "Create new employer")
  @PostMapping()
  @ApiResponse(responseCode = "201", description = "Success")
  public ResponseEntity<CommonDtoOut<EmployerDtoOut>> create(
    @Parameter(description = "Information of the new employer to be created", required = true)
    @Valid @RequestBody EmployerDtoIn dto) {
    CommonDtoOut<EmployerDtoOut> dtoOut = CommonDtoOut.create(this.employerService.create(dto));
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(dtoOut);
  }

  @Operation(summary = "Update employer information by ID")
  @PutMapping("/{id}")
  public ResponseEntity<CommonDtoOut<EmployerDtoOut>> update(
    @Parameter(description = "ID of the employer to update information for", required = true) @PathVariable("id") Integer id,
    @Valid @RequestBody UpdateEmployerDtoIn dto) {
    CommonDtoOut<EmployerDtoOut> dtoOut = CommonDtoOut.success(this.employerService.update(id, dto));
    return ResponseEntity
      .status(HttpStatus.valueOf(dtoOut.getStatusCode()))
      .body(dtoOut);
  }

  @Operation(summary = "Delete employer by ID")
  @DeleteMapping("/{id}")
  public ResponseEntity<CommonDtoOut<EmployerDtoOut>> delete(
    @Parameter(description = "ID of the employer to delete", required = true)
    @PathVariable("id") Integer id) {
    CommonDtoOut<EmployerDtoOut> dtoOut = CommonDtoOut.success(this.employerService.delete(id));
    return ResponseEntity
      .status(HttpStatus.valueOf(dtoOut.getStatusCode()))
      .body(dtoOut);
  }
}
