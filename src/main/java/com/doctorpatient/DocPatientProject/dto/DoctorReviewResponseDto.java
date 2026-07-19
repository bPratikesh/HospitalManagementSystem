package com.doctorpatient.DocPatientProject.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DoctorReviewResponseDto {

    private Long id;

    private Integer rating;

    private String review;

    private LocalDateTime createdAt;

    private Long appointmentId;

    private String patientName;

    private Long doctorId;

}