package com.doctorpatient.DocPatientProject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrescriptionRequestDto {

    private String diagnosis;
    private String prescribedMedicines;
    private String instructions;

}