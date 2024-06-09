package com.example.recruitment.api.service.employer;

import com.example.recruitment.api.dto.in.EmployerDtoIn;
import com.example.recruitment.api.dto.in.page.PageEmployerDtoIn;
import com.example.recruitment.api.dto.in.UpdateEmployerDtoIn;
import com.example.recruitment.api.dto.out.pagedata.DataEmployer;
import com.example.recruitment.api.dto.out.EmployerDtoOut;
import com.example.recruitment.common.dto.PageDtoOut;

public interface EmployerService {
    EmployerDtoOut get(Integer id);
    EmployerDtoOut create(EmployerDtoIn dto);
    EmployerDtoOut update(Integer id, UpdateEmployerDtoIn dto);
    EmployerDtoOut delete(Integer id);
    PageDtoOut<DataEmployer> list(PageEmployerDtoIn dto);


}
