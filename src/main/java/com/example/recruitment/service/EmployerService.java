package com.example.recruitment.service;

import com.example.recruitment.common.dto.CommonDtoOut;
import com.example.recruitment.dto.in.EmployerDtoIn;
import com.example.recruitment.dto.in.page.PageEmployerDtoIn;
import com.example.recruitment.dto.in.UpdateEmployerDtoIn;
import com.example.recruitment.dto.out.pagedata.DataEmployer;
import com.example.recruitment.dto.out.EmployerDtoOut;
import com.example.recruitment.common.dto.PageDtoOut;

public interface EmployerService {
    EmployerDtoOut get(Integer id);
    EmployerDtoOut create(EmployerDtoIn dto);
    EmployerDtoOut update(Integer id, UpdateEmployerDtoIn dto);
    EmployerDtoOut delete(Integer id);
    PageDtoOut<DataEmployer> list(PageEmployerDtoIn dto);


}
