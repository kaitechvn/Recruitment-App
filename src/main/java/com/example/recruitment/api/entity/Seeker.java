package com.example.recruitment.api.entity;

import com.example.recruitment.api.dto.in.SeekerDtoIn;
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
@Table(name = "seeker")
public class Seeker {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  private String name;
  private String birthday;
  private String address;
  private Integer province;
  @CreationTimestamp
  private Date created_at;
  @UpdateTimestamp
  private Date updated_at;

  public static Seeker fromDto(SeekerDtoIn dto){
    return Seeker.builder()
      .name(dto.getName())
      .birthday(dto.getBirthday())
      .address(dto.getAddress())
      .province(dto.getProvince())
      .build();
  }
}
