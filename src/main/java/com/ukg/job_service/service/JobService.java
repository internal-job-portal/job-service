package com.ukg.job_service.service;

import com.ukg.job_service.client.CandidateClient;
import com.ukg.job_service.model.Job;
import com.ukg.job_service.repository.JobRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class JobService {
    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private CandidateClient candidateClient;

    @Transactional(readOnly = true)
    public Job getJobById(Long id) {
        return jobRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Job not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<Job> getAllJobs() {
        return jobRepository.findAllByOrderByUpdatedAtDesc();
    }

    @Transactional(readOnly = true)
    public List<Job> getJobsByStatus(String status) {
        return jobRepository.findByStatusOrderByUpdatedAtDesc(status);
    }

    @Transactional
    public Job createJob(Job job) {
        job.setCreatedAt(LocalDateTime.now());
        job.setUpdatedAt(LocalDateTime.now());
        return jobRepository.save(job);
    }

    @Transactional
    public Job updateJob(Long id, Job jobDetails) {
        Job job = getJobById(id);
        job.setTitle(jobDetails.getTitle());
        job.setDescription(jobDetails.getDescription());
        job.setDesignation(jobDetails.getDesignation());
        job.setLocation(jobDetails.getLocation());
        job.setRequiredSkills(jobDetails.getRequiredSkills());
        job.setMinExperience(jobDetails.getMinExperience());
        job.setLanguagesRequired(jobDetails.getLanguagesRequired());
        job.setSalaryMin(jobDetails.getSalaryMin());
        job.setSalaryMax(jobDetails.getSalaryMax());
        job.setStatus(jobDetails.getStatus());
        job.setUpdatedAt(LocalDateTime.now());
        return jobRepository.save(job);
    }

    @Transactional
    public void closeJob(Long id) {
        Job job = getJobById(id);
        job.setStatus("CLOSED");
        job.setUpdatedAt(LocalDateTime.now());
        jobRepository.save(job);
    }

    @Transactional
    public void deleteJob(Long jobId) {
        
        boolean candidatesDeletionSuccessful = candidateClient.deleteCandidatesByJobId(jobId);
        
        if (candidatesDeletionSuccessful) {
            jobRepository.deleteById(jobId);
        } else {
            throw new RuntimeException("Failed to delete associated candidates. Job deletion aborted.");
        }
    }

    @Transactional(readOnly = true)
    public List<Job> getJobsForCandidate(Long employeeId) {
        List<Long> jobIds = candidateClient.getJobIdsForCandidate(employeeId);
        return jobRepository.findAllById(jobIds);
    }
}