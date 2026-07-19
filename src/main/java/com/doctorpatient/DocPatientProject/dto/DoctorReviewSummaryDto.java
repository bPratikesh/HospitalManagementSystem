package com.doctorpatient.DocPatientProject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DoctorReviewSummaryDto {

    private Double averageRating;

    private Long totalReviews;

}
