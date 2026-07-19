package com.doctorpatient.DocPatientProject.service;

import com.doctorpatient.DocPatientProject.dto.*;
import com.doctorpatient.DocPatientProject.entity.*;
import com.doctorpatient.DocPatientProject.entity.enums.AppointmentStatus;
import com.doctorpatient.DocPatientProject.entity.enums.PaymentStatus;
import com.doctorpatient.DocPatientProject.exception.BadRequestException;
import com.doctorpatient.DocPatientProject.exception.ResourceNotFoundException;
import com.doctorpatient.DocPatientProject.repository.AppointmentRepo;
import com.doctorpatient.DocPatientProject.repository.DoctorReviewRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorReviewService {

    private final DoctorReviewRepo doctorReviewRepo;
    private final AppointmentRepo appointmentRepo;
    private final ModelMapper modelMapper;

    public DoctorReviewResponseDto submitReview(Long appointmentId, DoctorReviewRequestDto requestDto) {

        Appointment appointment = appointmentRepo.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id : " + appointmentId));

        if (appointment.getStatus() != AppointmentStatus.COMPLETED) {
            throw new BadRequestException("Review can only be submitted after appointment completion.");
        }

        if (appointment.getPaymentStatus() != PaymentStatus.PAID) {
            throw new BadRequestException("Please complete payment before submitting review.");
        }

        if (doctorReviewRepo.existsByAppointmentId(appointmentId)) {
            throw new BadRequestException("Review already submitted for this appointment.");
        }

        DoctorReview review = new DoctorReview();

        review.setRating(requestDto.getRating());
        review.setReview(requestDto.getReview());
        review.setCreatedAt(LocalDateTime.now());

        review.setDoctor(appointment.getDoctor());
        review.setPatient(appointment.getPatient());
        review.setAppointment(appointment);

        DoctorReview savedReview = doctorReviewRepo.save(review);

        DoctorReviewResponseDto response = modelMapper.map(savedReview, DoctorReviewResponseDto.class);

        response.setAppointmentId(appointment.getId());
        response.setDoctorId(appointment.getDoctor().getId());
        response.setPatientName(appointment.getPatient().getUser().getName());

        return response;
    }

    public List<DoctorReviewResponseDto> getDoctorReviews(Long doctorId) {

        return doctorReviewRepo.findByDoctorIdOrderByCreatedAtDesc(doctorId)
                .stream()
                .map(review -> {
                    DoctorReviewResponseDto dto = modelMapper.map(review, DoctorReviewResponseDto.class);

                    dto.setAppointmentId(review.getAppointment().getId());
                    dto.setDoctorId(review.getDoctor().getId());
                    dto.setPatientName(review.getPatient().getUser().getName());
                    return dto;
                }).collect(Collectors.toList());
    }

    public DoctorReviewSummaryDto getDoctorSummary(Long doctorId) {

        Double avg = doctorReviewRepo.findAverageRatingByDoctorId(doctorId);
        Long count = doctorReviewRepo.countByDoctorId(doctorId);

        return new DoctorReviewSummaryDto(avg == null ? 0.0 : avg, count);
    }

}