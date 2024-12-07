package com.ukg.job_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ukg.job_service.config.FeignClientConfig;

import java.util.List;

@FeignClient(name = "candidate-service", configuration = FeignClientConfig.class)
public interface CandidateClient {
    @DeleteMapping("/api/candidates/jobservice/{jobId}")
    boolean deleteCandidatesByJobId(@PathVariable("jobId") Long jobId);

    @GetMapping("/api/candidates/jobservice/{employeeId}")
    List<Long> getJobIdsForCandidate(@PathVariable("employeeId") Long employeeId);
}
