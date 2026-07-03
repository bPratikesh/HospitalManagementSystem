package com.doctorpatient.DocPatientProject.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PrescriptionResponseDto {

    private Long prescriptionId;
    private String diagnosis;
    private String prescribedMedicines;
    private String instructions;
    private LocalDate prescriptionDate;
    private AppointmentResponseDto appointment;

}