package com.doctorpatient.DocPatientProject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrescriptionRequestDto {

    @NotBlank(message = "Diagnosis is required")
    private String diagnosis;

    @NotBlank(message = "Prescribed medicines are required")
    @Size(max = 3000, message = "Medicines field is too long")
    private String prescribedMedicines;

    @NotBlank(message = "Instructions are required")
    @Size(max = 1000, message = "Instructions field is too long")
    private String instructions;

}