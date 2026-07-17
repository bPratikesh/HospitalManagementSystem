package com.doctorpatient.DocPatientProject.dto;

import com.doctorpatient.DocPatientProject.entity.enums.AppointmentSlot;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AppointmentRequestDto {

    @NotBlank(message = "Symptoms are required")
    private String symptoms;

    @NotNull(message = "Appointment date is required")
    @FutureOrPresent(message = "Appointment date cannot be in the past")
    private LocalDate appointmentDate;

    @NotNull(message = "Appointment slot is required")
    private AppointmentSlot appointmentSlot;
}