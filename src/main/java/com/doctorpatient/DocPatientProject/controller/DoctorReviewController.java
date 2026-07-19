package com.doctorpatient.DocPatientProject.controller;

import com.doctorpatient.DocPatientProject.dto.DoctorReviewRequestDto;
import com.doctorpatient.DocPatientProject.dto.DoctorReviewResponseDto;
import com.doctorpatient.DocPatientProject.dto.DoctorReviewSummaryDto;
import com.doctorpatient.DocPatientProject.service.DoctorReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class DoctorReviewController {

    private final DoctorReviewService doctorReviewService;

    @PostMapping("/appointment/{appointmentId}")
    public DoctorReviewResponseDto submitReview( @PathVariable Long appointmentId, @Valid @RequestBody DoctorReviewRequestDto requestDto) {
        return doctorReviewService.submitReview(appointmentId, requestDto);
    }

    @GetMapping("/doctor/{doctorId}")
    public List<DoctorReviewResponseDto> getDoctorReviews(@PathVariable Long doctorId) {
        return doctorReviewService.getDoctorReviews(doctorId);
    }

    @GetMapping("/doctor/{doctorId}/summary")
    public DoctorReviewSummaryDto getDoctorSummary(@PathVariable Long doctorId) {
        return doctorReviewService.getDoctorSummary(doctorId);
    }

}