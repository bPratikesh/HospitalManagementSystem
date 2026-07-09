package com.doctorpatient.DocPatientProject.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDoctorRequestDto {

    // User info
//    @NotBlank(message = "name is required")
//    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Please enter a valid email address")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
    private String password;

    // Doctor infoo
    @NotBlank(message = "Doctor name is required")
    private String doctorName;

    @NotNull(message = "Experience is required")
    @Min(value = 0, message = "Experience cannot be negative")
    private Long experience;

    @NotBlank(message = "Speciality is required")
    private String speciality;

    @NotNull(message = "Consultation fee is required")
    @DecimalMin(value = "0.0", message = "Consultation fee must be greater than 0")
    private Double consultationFee;
}