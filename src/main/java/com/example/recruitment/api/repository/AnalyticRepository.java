package com.example.recruitment.api.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.recruitment.api.dto.out.analytic.Analytic;
import com.example.recruitment.api.dto.out.analytic.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class AnalyticRepository {

  @Autowired
  private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  public Analytic getAnalytic(Date fromDate, Date toDate) {
    String summarySql = """
    SELECT
        (SELECT COUNT(id) FROM employer WHERE created_at BETWEEN :fromDate AND :toDate) AS employer_count,
        (SELECT COUNT(id) FROM jobs WHERE created_at BETWEEN :fromDate AND :toDate) AS job_count,
        (SELECT COUNT(id) FROM seeker WHERE created_at BETWEEN :fromDate AND :toDate) AS seeker_count,
        (SELECT COUNT(id) FROM resume WHERE created_at BETWEEN :fromDate AND :toDate) AS resume_count
    """;

    String chartSql = """
      WITH RECURSIVE date_series AS (
                                 SELECT :fromDate AS date
                                 UNION ALL
                                 SELECT DATE_ADD(date, INTERVAL 1 DAY)
                                 FROM date_series
                                 WHERE DATE_ADD(date, INTERVAL 1 DAY) < :toDate
                             ),
                             employer_counts ASAnh th (
                                 SELECT DATE(created_at) AS date, COUNT(DISTINCT id) AS count
                                 FROM employer
                                 WHERE created_at BETWEEN :fromDate AND :toDate
                                 GROUP BY DATE(created_at)
                             ),
                             job_counts AS (
                                 SELECT DATE(created_at) AS date, COUNT(DISTINCT id) AS count
                                 FROM jobs
                                 WHERE created_at BETWEEN :fromDate AND :toDate
                                 GROUP BY DATE(created_at)
                             ),
                             seeker_counts AS (
                                 SELECT DATE(created_at) AS date, COUNT(DISTINCT id) AS count
                                 FROM seeker
                                 WHERE created_at BETWEEN :fromDate AND :toDate
                                 GROUP BY DATE(created_at)
                             ),
                             resume_counts AS (
                                 SELECT DATE(created_at) AS date, COUNT(DISTINCT id) AS count
                                 FROM resume
                                 WHERE created_at BETWEEN :fromDate AND :toDate
                                 GROUP BY DATE(created_at)
                             )
                             SELECT
                                 ds.date,
                                 COALESCE(ec.count, 0) AS employer_count,
                                 COALESCE(jc.count, 0) AS job_count,
                                 COALESCE(sc.count, 0) AS seeker_count,
                                 COALESCE(rc.count, 0) AS resume_count
                             FROM
                                 date_series ds
                             LEFT JOIN employer_counts ec ON ds.date = ec.date
                             LEFT JOIN job_counts jc ON ds.date = jc.date
                             LEFT JOIN seeker_counts sc ON ds.date = sc.date
                             LEFT JOIN resume_counts rc ON ds.date = rc.date
                             HAVING
                                 employer_count > 0 OR
                                 job_count > 0 OR
                                 seeker_count > 0 OR
                                 resume_count > 0
                             ORDER BY ds.date;
      """;

    MapSqlParameterSource params = new MapSqlParameterSource();
    params.addValue("fromDate", fromDate);
    params.addValue("toDate", toDate);

    Analytic analytic = namedParameterJdbcTemplate.queryForObject(summarySql, params, new AnalyticRowMapper());

    List<Element> chart = namedParameterJdbcTemplate.query(chartSql, params, new ElementRowMapper());
    analytic.setChart(chart);

    return analytic;
  }

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

}
