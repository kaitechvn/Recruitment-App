package com.example.recruitment.analytic;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AnalyticRowMapper implements RowMapper<Analytic> {
  @Override
  public Analytic mapRow(ResultSet rs, int rowNum) throws SQLException {
    Analytic analytic = new Analytic();
    analytic.setNumEmployer(rs.getInt("employer_count"));
    analytic.setNumJob(rs.getInt("job_count"));
    analytic.setNumSeeker(rs.getInt("seeker_count"));
    analytic.setNumResume(rs.getInt("resume_count"));
    return analytic;
  }
}
