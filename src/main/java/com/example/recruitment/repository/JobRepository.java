package com.example.recruitment.repository;
import org.springframework.data.domain.Pageable;
import com.example.recruitment.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {
  Page<Job> findAllByEmployerId(Integer id, Pageable page);
}
