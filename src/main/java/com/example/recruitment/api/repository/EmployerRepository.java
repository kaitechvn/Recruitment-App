package com.example.recruitment.api.repository;

import com.example.recruitment.api.entity.Employer;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, Integer> {
  Optional<Employer> findByEmail(String email);

  @Cacheable(value = "employer", key = "#paging")
  Page<Employer> findAll(Pageable paging);

  @Cacheable(value = "employer", key = "#id")
  Optional<Employer> findById(Integer id);

  @Override
  @CachePut(value = "employer", key = "#employer.id")
  <S extends Employer> S save(S employer);

  @Override
  @CacheEvict(value = "employer", key = "#id")
  void deleteById(Integer id);

}



