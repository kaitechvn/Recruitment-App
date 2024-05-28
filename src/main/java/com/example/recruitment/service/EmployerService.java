package com.example.recruitment.service;

import com.example.recruitment.common.dto.CommonDtoOut;
import com.example.recruitment.dto.in.EmployerDtoIn;
import com.example.recruitment.dto.in.page.PageEmployerDtoIn;
import com.example.recruitment.dto.in.UpdateEmployerDtoIn;
import com.example.recruitment.dto.out.pagedata.DataEmployer;
import com.example.recruitment.dto.out.EmployerDtoOut;
import com.example.recruitment.common.dto.PageDtoOut;

public interface EmployerService {
    CommonDtoOut<EmployerDtoOut> get(Integer id);
    CommonDtoOut<EmployerDtoOut> create(EmployerDtoIn dto);
    CommonDtoOut<EmployerDtoOut> update(Integer id, UpdateEmployerDtoIn dto);
    CommonDtoOut<EmployerDtoOut> delete(Integer id);
    CommonDtoOut<PageDtoOut<DataEmployer>> list(PageEmployerDtoIn dto);


}
