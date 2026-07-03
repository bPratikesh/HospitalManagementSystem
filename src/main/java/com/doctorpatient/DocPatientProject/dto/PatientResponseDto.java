package com.doctorpatient.DocPatientProject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientResponseDto {

    private Long id;
    private String patientName;
    private Long age;
    private String gender;
    private String bloodGroup;
    private UserResponseDto user;

}