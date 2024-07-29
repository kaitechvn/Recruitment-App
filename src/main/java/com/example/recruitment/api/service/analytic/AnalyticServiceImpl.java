package com.example.recruitment.api.service.analytic;

import com.example.recruitment.api.repository.EmployerRepository;
import com.example.recruitment.api.repository.JobRepository;
import com.example.recruitment.api.repository.ResumeRepository;
import com.example.recruitment.api.repository.SeekerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AnalyticServiceImpl implements AnalyticService {

  private final EmployerRepository employerRepository;
  private final JobRepository jobRepository;
  private final SeekerRepository seekerRepository;
  private final ResumeRepository resumeRepository;

  @Autowired
  public AnalyticServiceImpl(EmployerRepository employerRepository, JobRepository jobRepository, SeekerRepository seekerRepository, ResumeRepository resumeRepository) {
    this.employerRepository = employerRepository;
    this.jobRepository = jobRepository;
    this.seekerRepository = seekerRepository;
    this.resumeRepository = resumeRepository;
  }

  public AnalyticResponse getAnalytics(LocalDate fromDate, LocalDate toDate) {
    AtomicLong sumEmployerCount = new AtomicLong(0);
    AtomicLong sumJobCount = new AtomicLong(0);
    AtomicLong sumSeekerCount = new AtomicLong(0);
    AtomicLong sumResumeCount = new AtomicLong(0);

    List<AnalyticData> analyticsData = generateDateSeries(fromDate, toDate).stream()
      .map(date -> {
        long employerCount = employerRepository.countByCreatedAt(date);
        long jobCount = jobRepository.countByCreatedAt(date);
        long seekerCount = seekerRepository.countByCreatedAt(date);
        long resumeCount = resumeRepository.countByCreatedAt(date);

        sumEmployerCount.addAndGet(employerCount);
        sumJobCount.addAndGet(jobCount);
        sumSeekerCount.addAndGet(seekerCount);
        sumResumeCount.addAndGet(resumeCount);

        return new AnalyticData(date, employerCount, jobCount, seekerCount, resumeCount);
      })
      .collect(Collectors.toList());

    return new AnalyticResponse(
      sumEmployerCount.get(),
      sumJobCount.get(),
      sumSeekerCount.get(),
      sumResumeCount.get(),
      analyticsData
    );
  }

  private List<LocalDate> generateDateSeries(LocalDate fromDate, LocalDate toDate) {
    return Stream.iterate(fromDate, date -> date.plusDays(1))
      .limit(ChronoUnit.DAYS.between(fromDate, toDate) + 1)
      .collect(Collectors.toList());
  }

//    long sumEmployerCount = data.stream().mapToLong(AnalyticData::getEmployerCount).sum();
//    long sumJobCount = data.stream().mapToLong(AnalyticData::getJobCount).sum();
//    long sumSeekerCount = data.stream().mapToLong(AnalyticData::getSeekerCount).sum();
//    long sumResumeCount = data.stream().mapToLong(AnalyticData::getResumeCount).sum();
//
//    return new AnalyticResponse(sumEmployerCount, sumJobCount, sumSeekerCount, sumResumeCount, data);
}
