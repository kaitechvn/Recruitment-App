package com.example.recruitment.api.entity;

import com.example.recruitment.common.data_transform.Converter;
import com.example.recruitment.api.dto.in.JobDtoIn;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "jobs")
public class Job {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(name = "employer_id")
  private Integer employerId;

  private String title;
  private Integer quantity;
  private String description;
  private Integer salary;
  private String fields;
  private String provinces;

  @CreationTimestamp
  private Date created_at;
  @UpdateTimestamp
  private Date updated_at;

  @Column(name = "expired_at")
  private Date expiredAt;


  public static Job fromDto(JobDtoIn dto) {
    return Job.builder()
      .employerId(dto.getEmployerId())
      .title(dto.getTitle())
      .quantity(dto.getQuantity())
      .description(dto.getDescription())
      .salary(dto.getSalary())
      .fields(Converter.ListToStringDb(dto.getFieldIds()))
      .provinces(Converter.ListToStringDb(dto.getProvinceIds()))
      .expiredAt(dto.getExpiredAt())
      .build();
  }

}
