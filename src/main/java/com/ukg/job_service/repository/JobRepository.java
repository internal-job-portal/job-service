package com.ukg.job_service.repository;

import com.ukg.job_service.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findAllByOrderByUpdatedAtDesc();
    List<Job> findByStatusOrderByUpdatedAtDesc(String status);
}
