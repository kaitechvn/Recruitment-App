package com.example.recruitment.api.controller;

import com.example.recruitment.common.dto.CommonDtoOut;
import com.example.recruitment.common.dto.PageDtoOut;
import com.example.recruitment.api.dto.in.page.PageSeekerDtoIn;
import com.example.recruitment.api.dto.in.SeekerDtoIn;
import com.example.recruitment.api.dto.out.pagedata.DataSeeker;
import com.example.recruitment.api.dto.out.SeekerDtoOut;
import com.example.recruitment.api.service.seeker.SeekerService;
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

@Tag(name = "Seeker", description = "Manage seekers")
@SecurityRequirement(name = "Authorization")
@RestController
@RequestMapping("/seeker")
public class SeekerController {

  @Autowired
  private SeekerService seekerService;

  @Operation(summary = "Get seeker information by ID")
  @ApiResponse(responseCode = "200", description = "Success")
  @GetMapping("{id}")
  public ResponseEntity<CommonDtoOut<SeekerDtoOut>> get(
    @Parameter(description = "ID of the seeker to retrieve information for", required = true)
    @PathVariable("id") Integer id) {
    CommonDtoOut<SeekerDtoOut> dtoOut = CommonDtoOut.success(seekerService.get(id));
    return new ResponseEntity<>(
      dtoOut, HttpStatus.valueOf(dtoOut.getStatusCode())
    );
  }

  @Operation(summary = "Create a new seeker")
  @ApiResponse(responseCode = "201", description = "Created")
  @PostMapping
  public ResponseEntity<CommonDtoOut<SeekerDtoOut>> create(
    @Parameter(description = "Information of the new seeker to be created", required = true)
    @RequestBody SeekerDtoIn dtoIn) {
    CommonDtoOut<SeekerDtoOut> dtoOut = CommonDtoOut.create(seekerService.create(dtoIn));
    return new ResponseEntity<>(dtoOut, HttpStatus.valueOf(dtoOut.getStatusCode()));
  }

  @Operation(summary = "Update seeker information by ID")
  @ApiResponse(responseCode = "200", description = "Success")
  @PutMapping("{id}")
  public ResponseEntity<CommonDtoOut<SeekerDtoOut>> update(
    @Parameter(description = "ID of the seeker to update information for", required = true) @PathVariable("id") Integer id,
    @Parameter(description = "Updated information of the seeker", required = true) @RequestBody SeekerDtoIn dtoIn) {
    CommonDtoOut<SeekerDtoOut> dtoOut = CommonDtoOut.success(seekerService.update(id, dtoIn));
    return new ResponseEntity<>(dtoOut, HttpStatus.valueOf(dtoOut.getStatusCode()));
  }

  @Operation(summary = "Delete seeker by ID")
  @ApiResponse(responseCode = "200", description = "Success")
  @DeleteMapping("{id}")
  public ResponseEntity<CommonDtoOut<SeekerDtoOut>> delete(
    @Parameter(description = "ID of the seeker to delete", required = true) @PathVariable("id") Integer id) {
    CommonDtoOut<SeekerDtoOut> dtoOut = CommonDtoOut.success(seekerService.delete(id));
    return new ResponseEntity<>(dtoOut, HttpStatus.valueOf(dtoOut.getStatusCode()));
  }

  @Operation(summary = "Get list of seekers")
  @ApiResponse(responseCode = "200", description = "Success")
  @GetMapping()
  public ResponseEntity<CommonDtoOut<PageDtoOut<DataSeeker>>> list(
    @Parameter(description = "Pagination information") @Valid PageSeekerDtoIn dto){
    CommonDtoOut<PageDtoOut<DataSeeker>> dtoOut = CommonDtoOut.success(seekerService.list(dto));
    return new ResponseEntity<>(
      dtoOut, HttpStatus.valueOf(dtoOut.getStatusCode())
    );
  }
}
