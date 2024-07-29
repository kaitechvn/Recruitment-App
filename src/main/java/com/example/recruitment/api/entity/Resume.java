package com.example.recruitment.api.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
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
  @Column(name = "created_at")
  private LocalDate createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private LocalDate updatedAt;

}
