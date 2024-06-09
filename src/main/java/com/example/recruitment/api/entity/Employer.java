package com.example.recruitment.api.entity;

import com.example.recruitment.api.dto.in.EmployerDtoIn;
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
@Table(name = "employer")
public class Employer  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(unique = true)
    private  String email;
    private String name;
    private Integer province;
    private String description;
    @CreationTimestamp
    private Date created_at;
    @UpdateTimestamp
    private Date updated_at;


    public static Employer fromDto(EmployerDtoIn dto){
        return Employer.builder()
                .email(dto.getEmail())
                .name(dto.getName())
                .province(dto.getProvinceId())
                .description(dto.getDescription())
                .build();
    }


}
