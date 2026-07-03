package com.doctorpatient.DocPatientProject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientRequestDto {

    private String patientName;
    private Long age;
    private String gender;
    private String bloodGroup;

}