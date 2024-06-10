package com.example.recruitment.api.entity;

import com.example.recruitment.common.data_transform.Converter;
import com.example.recruitment.api.dto.in.ResumeDtoIn;
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
@Table(name = "resume")
public class Resume {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(name = "seeker_id")
  private Integer seekerId;

  @Column(name = "career_obj")
  private String careerObj;

  private String title;
  private Integer salary;
  private String fields;
  private String provinces;

  @CreationTimestamp
  private Date created_at;
  @UpdateTimestamp
  private Date updated_at;

  public static Resume fromDto(ResumeDtoIn dto) {
      return Resume.builder()
        .seekerId(dto.getSeekerId())
        .careerObj(dto.getCareerObj())
        .salary(dto.getSalary())
        .title(dto.getTitle())
        .fields(Converter.ListToStringDb(dto.getFieldIds()))
        .provinces(Converter.ListToStringDb(dto.getProvinceIds()))
        .build();

  }

}
