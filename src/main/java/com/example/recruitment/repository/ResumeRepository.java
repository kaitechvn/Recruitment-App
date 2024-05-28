package com.example.recruitment.repository;

import com.example.recruitment.entity.Resume;
import com.example.recruitment.entity.Seeker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Integer> {
  Page<Resume> findAllBySeekerId(Integer id, Pageable page);

}
