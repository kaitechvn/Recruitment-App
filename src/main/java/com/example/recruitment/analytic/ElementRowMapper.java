package com.example.recruitment.analytic;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ElementRowMapper implements RowMapper<Element> {
  @Override
  public Element mapRow(ResultSet rs, int rowNum) throws SQLException {
    Element element = new Element();
    element.setDate(rs.getDate("date"));
    element.setNumEmployer(rs.getInt("employer_count"));
    element.setNumJob(rs.getInt("job_count"));
    element.setNumSeeker(rs.getInt("seeker_count"));
    element.setNumResume(rs.getInt("resume_count"));
    return element;
  }
}
