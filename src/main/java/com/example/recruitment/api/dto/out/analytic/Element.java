package com.example.recruitment.api.dto.out.analytic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Element {
  private Date date;
  private Integer numEmployer;
  private Integer numJob;
  private Integer numSeeker;
  private Integer numResume;

}
