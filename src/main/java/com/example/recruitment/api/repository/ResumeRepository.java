package com.example.recruitment.api.repository;

import com.example.recruitment.api.entity.Resume;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Integer> {
  Page<Resume> findAllBySeekerId(Integer id, Pageable page);

  @Query("SELECT COUNT(r) FROM Resume r WHERE DATE(r.createdAt) = :date")
  long countByCreatedAt(@Param("date") LocalDate date);

}
