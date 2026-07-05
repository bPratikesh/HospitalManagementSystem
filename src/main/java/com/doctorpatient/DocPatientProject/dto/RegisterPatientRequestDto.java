package com.doctorpatient.DocPatientProject.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterPatientRequestDto {

    // User
    @NotBlank(message = "name is required")
    private String name;
    @NotBlank(message = "Email is required")
    @Email(message = "Please enter a valid email address")
    private String email;
    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
    private String password;

    // Patient
    @NotBlank(message = "Patient name is required")
    private String patientName;

    @NotNull(message = "Age is required")
    @Min(value = 0, message = "Age cannot be negative")
    @Max(value = 120, message = "Invalid age")
    private Long age;

    @NotBlank(message = "Gender is required")
    private String gender;

    @NotBlank(message = "Blood group is required")
    private String bloodGroup;
}