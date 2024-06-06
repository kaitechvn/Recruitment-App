package com.example.recruitment.controller;

import com.example.recruitment.common.dto.CommonDtoOut;
import com.example.recruitment.common.dto.PageDtoOut;
import com.example.recruitment.dto.in.JobDtoIn;
import com.example.recruitment.dto.in.page.PageJobDtoIn;
import com.example.recruitment.dto.out.pagedata.DataJob;
import com.example.recruitment.dto.out.JobDtoOut;
import com.example.recruitment.service.job.JobService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/job")
public class JobController {
  @Autowired
  private JobService jobService;

  @GetMapping(value = "/{id}")
  public ResponseEntity<CommonDtoOut<JobDtoOut>> get(@PathVariable("id") Integer id) {
    CommonDtoOut<JobDtoOut> dtoOut = CommonDtoOut.success(this.jobService.get(id));
    return new ResponseEntity<>(
      dtoOut, HttpStatusCode.valueOf((dtoOut.getStatusCode()))
    );
  }

  @GetMapping()
  public ResponseEntity<CommonDtoOut<PageDtoOut<DataJob>>> list(@Valid PageJobDtoIn dto){
    CommonDtoOut<PageDtoOut<DataJob>> dtoOut = CommonDtoOut.success(this.jobService.list(dto));
    return new ResponseEntity<>(
      dtoOut, HttpStatusCode.valueOf(dtoOut.getStatusCode())
    );
  }

  @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
  public ResponseEntity<CommonDtoOut<JobDtoOut>> create(@Valid @RequestBody JobDtoIn dto) {
    CommonDtoOut<JobDtoOut> dtoOut  = CommonDtoOut.create(this.jobService.create(dto));
    return new ResponseEntity<>(
      dtoOut, HttpStatusCode.valueOf(dtoOut.getStatusCode())
    );
  }

  @PutMapping("{id}")
  public ResponseEntity<CommonDtoOut<JobDtoOut>> update(@PathVariable("id") Integer id, @RequestBody JobDtoIn dto) {
    CommonDtoOut<JobDtoOut> dtoOut = CommonDtoOut.success(jobService.update(id, dto));
    return new ResponseEntity<>(
      dtoOut, HttpStatusCode.valueOf(dtoOut.getStatusCode())
    );
  }

  @DeleteMapping("{id}")
  public ResponseEntity<CommonDtoOut<JobDtoOut>> delete(@PathVariable("id") Integer id) {
    CommonDtoOut<JobDtoOut> dtoOut = CommonDtoOut.success(jobService.delete(id));
    return new ResponseEntity<>(
      dtoOut, HttpStatusCode.valueOf(dtoOut.getStatusCode())
    );
  }



}
