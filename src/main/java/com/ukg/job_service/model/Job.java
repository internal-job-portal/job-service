package com.ukg.job_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    @Column(columnDefinition = "TEXT")
    private String description;

    @NotBlank(message = "Designation is required")
    private String designation;

    @NotBlank(message = "Location is required")
    private String location;

    @ElementCollection
    private List<String> requiredSkills;

    @Min(value = 0, message = "Minimum experience cannot be negative")
    private Integer minExperience;

    @ElementCollection
    private List<String> languagesRequired;

    @NotNull(message = "Minimum salary is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Minimum salary must be greater than zero")
    private BigDecimal salaryMin;

    @NotNull(message = "Maximum salary is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Maximum salary must be greater than zero")
    private BigDecimal salaryMax;

    @NotBlank(message = "Status is required")
    private String status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
