package com.doctorpatient.DocPatientProject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorResponseDto {

    private Long id;
    private String doctorName;
    private Long experience;
    private String speciality;
    private Double consultationFee;
    private UserResponseDto user;

}