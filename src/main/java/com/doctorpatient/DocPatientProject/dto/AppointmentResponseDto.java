package com.doctorpatient.DocPatientProject.dto;

import com.doctorpatient.DocPatientProject.entity.enums.AppointmentStatus;
import com.doctorpatient.DocPatientProject.entity.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AppointmentResponseDto {

    private Long id;
    private String symptoms;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime time;
    private DoctorResponseDto doctor;
    private PatientResponseDto patient;
    private AppointmentStatus status;
    private PaymentStatus paymentStatus;

}