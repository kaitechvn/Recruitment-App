package com.example.recruitment.repository;

import com.example.recruitment.entity.Job;
import com.example.recruitment.entity.Seeker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeekerRepository extends JpaRepository<Seeker, Integer> {
  Page<Seeker> findAllByProvince(Integer id, Pageable page);
}
