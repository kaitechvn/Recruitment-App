package com.example.recruitment.api.repository;

import com.example.recruitment.api.entity.Seeker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SeekerRepository extends JpaRepository<Seeker, Integer> {
  Page<Seeker> findAllByProvince(Integer id, Pageable page);

  @Query("SELECT COUNT(s) FROM Seeker s WHERE DATE(s.createdAt) = :date")
  long countByCreatedAt(@Param("date") LocalDate date);

}

