package com.example.recruitment.api.controller;

import com.example.recruitment.common.dto.CommonDtoOut;
import com.example.recruitment.common.dto.PageDtoOut;
import com.example.recruitment.api.dto.in.page.PageSeekerDtoIn;
import com.example.recruitment.api.dto.in.SeekerDtoIn;
import com.example.recruitment.api.dto.out.pagedata.DataSeeker;
import com.example.recruitment.api.dto.out.SeekerDtoOut;
import com.example.recruitment.api.service.seeker.SeekerService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Seeker", description = "Quản lý seeker")
@SecurityRequirement(name = "Authorization")
@RestController
@RequestMapping("/seeker")
public class SeekerController {

  @Autowired
  private SeekerService seekerService;

  @GetMapping("{id}")
  public ResponseEntity<CommonDtoOut<SeekerDtoOut>> get(@PathVariable("id") Integer id) {
    CommonDtoOut<SeekerDtoOut> dtoOut = CommonDtoOut.success(seekerService.get(id));
    return new ResponseEntity<>(
      dtoOut, HttpStatusCode.valueOf(dtoOut.getStatusCode())
    );
  }

  @PostMapping
  public ResponseEntity<CommonDtoOut<SeekerDtoOut>> create(@RequestBody SeekerDtoIn dtoIn) {
    CommonDtoOut<SeekerDtoOut> dtoOut = CommonDtoOut.create(seekerService.create(dtoIn));
    return new ResponseEntity<>(dtoOut, HttpStatusCode.valueOf(dtoOut.getStatusCode()));
  }

  @PutMapping("{id}")
  public ResponseEntity<CommonDtoOut<SeekerDtoOut>> update(@PathVariable("id") Integer id, @RequestBody SeekerDtoIn dtoIn) {
    CommonDtoOut<SeekerDtoOut> dtoOut = CommonDtoOut.success(seekerService.update(id, dtoIn));
    return new ResponseEntity<>(dtoOut, HttpStatusCode.valueOf(dtoOut.getStatusCode()));
  }

  @DeleteMapping("{id}")
  public ResponseEntity<CommonDtoOut<SeekerDtoOut>> delete(@PathVariable("id") Integer id) {
    CommonDtoOut<SeekerDtoOut> dtoOut = CommonDtoOut.success(seekerService.delete(id));
    return new ResponseEntity<>(dtoOut, HttpStatusCode.valueOf(dtoOut.getStatusCode()));
  }

  @GetMapping()
  public ResponseEntity<CommonDtoOut<PageDtoOut<DataSeeker>>> list(@Valid PageSeekerDtoIn dto){
    CommonDtoOut<PageDtoOut<DataSeeker>> dtoOut = CommonDtoOut.success(seekerService.list(dto));
    return new ResponseEntity<>(
      dtoOut, HttpStatusCode.valueOf(dtoOut.getStatusCode())
    );
  }
}
