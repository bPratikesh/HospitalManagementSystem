package com.doctorpatient.DocPatientProject.controller;

import com.doctorpatient.DocPatientProject.dto.DoctorResponseDto;
import com.doctorpatient.DocPatientProject.dto.LoginRequestDto;
import com.doctorpatient.DocPatientProject.dto.LoginResponseDto;
import com.doctorpatient.DocPatientProject.dto.PatientResponseDto;
import com.doctorpatient.DocPatientProject.dto.RegisterDoctorRequestDto;
import com.doctorpatient.DocPatientProject.dto.RegisterPatientRequestDto;
import com.doctorpatient.DocPatientProject.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register/doctor")
    public DoctorResponseDto registerDoctor(@RequestBody @Valid RegisterDoctorRequestDto dto) {
        return authService.registerDoctor(dto);
    }

    @PostMapping("/register/patient")
    public PatientResponseDto registerPatient(@RequestBody @Valid RegisterPatientRequestDto dto) {
        return authService.registerPatient(dto);
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody @Valid LoginRequestDto dto) {
        return authService.login(dto);
    }
}