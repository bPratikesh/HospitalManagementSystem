package com.doctorpatient.DocPatientProject.dto;

import com.doctorpatient.DocPatientProject.entity.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {

    private Long userId;
    private Long doctorId;
    private Long patientId;

    private String name;
    private String email;
    private Role role;
}
