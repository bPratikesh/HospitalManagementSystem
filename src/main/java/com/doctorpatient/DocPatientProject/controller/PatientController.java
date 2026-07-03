package com.doctorpatient.DocPatientProject.controller;

import com.doctorpatient.DocPatientProject.dto.PatientRequestDto;
import com.doctorpatient.DocPatientProject.dto.PatientResponseDto;
import com.doctorpatient.DocPatientProject.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @PostMapping("/create/{userId}")
    public PatientResponseDto createPatient(@Valid @RequestBody PatientRequestDto patientRequestDto, @PathVariable Long userId) {
        return patientService.createPatient(patientRequestDto, userId);
    }

    @GetMapping("/{id}")
    public PatientResponseDto getPatientById(@PathVariable Long id) {
        return patientService.getPatientById(id);
    }

    @GetMapping("/getAll")
    public List<PatientResponseDto> getAllPatients() {
        return patientService.getAllPatients();
    }

    @PutMapping("/update/{id}")
    public PatientResponseDto updatePatient(@PathVariable Long id, @Valid @RequestBody PatientRequestDto patientRequestDto) {
        return patientService.updatePatient(id, patientRequestDto);
    }

    @DeleteMapping("/delete/{id}")
    public String deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return "Patient deleted successfully";
    }
}