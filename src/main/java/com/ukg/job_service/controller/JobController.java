package com.ukg.job_service.controller;

import com.ukg.job_service.model.Job;
import com.ukg.job_service.service.JobService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    @PostMapping
    public ResponseEntity<Job> createJob(@Valid @RequestBody Job job) {
        return ResponseEntity.ok(jobService.createJob(job));
    }

    @GetMapping
    public ResponseEntity<List<Job>> getJobs(@RequestParam(defaultValue = "All") String status) {
        List<Job> jobs;
        if ("All".equalsIgnoreCase(status)) {
            jobs = jobService.getAllJobs();
        } else {
            jobs = jobService.getJobsByStatus(status);
        }
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> getJob(@PathVariable Long id) {
        return ResponseEntity.ok(jobService.getJobById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Job> updateJob(@PathVariable Long id, @Valid @RequestBody Job jobDetails) {
        return ResponseEntity.ok(jobService.updateJob(id, jobDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/candidate/{employeeId}")
    public ResponseEntity<List<Job>> getCandidateJobs(@PathVariable Long employeeId) {
        List<Job> jobs = jobService.getJobsForCandidate(employeeId);
        return ResponseEntity.ok(jobs);
    }
    
}
