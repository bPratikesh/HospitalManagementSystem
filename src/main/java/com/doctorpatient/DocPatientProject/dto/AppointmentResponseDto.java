package com.doctorpatient.DocPatientProject.dto;

import com.doctorpatient.DocPatientProject.entity.enums.AppointmentSlot;
import com.doctorpatient.DocPatientProject.entity.enums.AppointmentStatus;
import com.doctorpatient.DocPatientProject.entity.enums.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AppointmentResponseDto {

    private Long id;
    private String symptoms;
    private LocalDate appointmentDate;
    private AppointmentSlot appointmentSlot;
    private DoctorResponseDto doctor;
    private PatientResponseDto patient;
    private AppointmentStatus status;
    private PaymentStatus paymentStatus;
}