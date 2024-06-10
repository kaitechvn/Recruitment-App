package com.example.recruitment.api.repository;
import org.springframework.data.domain.Pageable;
import com.example.recruitment.api.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Integer> {
  Page<Job> findAllByEmployerId(Integer id, Pageable page);
}
