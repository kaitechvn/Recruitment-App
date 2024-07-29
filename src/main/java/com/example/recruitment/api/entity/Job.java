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
  @Column(name = "created_at")
  private LocalDate createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private LocalDate updatedAt;


  @Column(name = "expired_at")
  private LocalDate expiredAt;

}
