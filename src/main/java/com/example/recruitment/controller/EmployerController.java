package com.example.recruitment.controller;

import com.example.recruitment.common.dto.CommonDtoOut;
import com.example.recruitment.dto.in.EmployerDtoIn;
import com.example.recruitment.dto.in.page.PageEmployerDtoIn;
import com.example.recruitment.dto.in.UpdateEmployerDtoIn;
import com.example.recruitment.dto.out.pagedata.DataEmployer;
import com.example.recruitment.dto.out.EmployerDtoOut;
import com.example.recruitment.common.dto.PageDtoOut;
import com.example.recruitment.service.employer.EmployerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/employer")
public class EmployerController {
@Autowired
    private EmployerService employerService;

    @GetMapping()
    public ResponseEntity<CommonDtoOut<PageDtoOut<DataEmployer>>> list(@Valid PageEmployerDtoIn pageDto) {
        CommonDtoOut<PageDtoOut<DataEmployer>> dtoOut = CommonDtoOut.success(this.employerService.list(pageDto));
        return new ResponseEntity<>(
                dtoOut, HttpStatusCode.valueOf(dtoOut.getStatusCode())
        );
    }

    @PostMapping()
    public ResponseEntity<CommonDtoOut<EmployerDtoOut>> create(@Valid @RequestBody EmployerDtoIn dto) {
        CommonDtoOut<EmployerDtoOut> dtoOut  = CommonDtoOut.create(this.employerService.create(dto));
        return new ResponseEntity<>(
                dtoOut, HttpStatusCode.valueOf(dtoOut.getStatusCode())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonDtoOut<EmployerDtoOut>> get(@PathVariable("id") Integer id) {
      CommonDtoOut dtoOut = CommonDtoOut.success(this.employerService.get(id));

        return new ResponseEntity<>(
                dtoOut, HttpStatusCode.valueOf(dtoOut.getStatusCode())
        );
    }


    @PutMapping("/{id}")
    public ResponseEntity<CommonDtoOut<EmployerDtoOut>> update(@PathVariable("id") Integer id, @RequestBody UpdateEmployerDtoIn dto) {
        CommonDtoOut<EmployerDtoOut> dtoOut = CommonDtoOut.success(this.employerService.update(id, dto));
        return new ResponseEntity<>(
                dtoOut, HttpStatusCode.valueOf(dtoOut.getStatusCode())
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonDtoOut<EmployerDtoOut>> delete(@PathVariable("id") Integer id) {
        CommonDtoOut<EmployerDtoOut> dtoOut  = CommonDtoOut.success(this.employerService.delete(id));
        return new ResponseEntity<>(
                dtoOut, HttpStatusCode.valueOf(dtoOut.getStatusCode())
        );
    }

}


