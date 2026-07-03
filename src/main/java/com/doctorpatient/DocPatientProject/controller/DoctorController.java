package com.doctorpatient.DocPatientProject.controller;

import com.doctorpatient.DocPatientProject.dto.DoctorRequestDto;
import com.doctorpatient.DocPatientProject.dto.DoctorResponseDto;
import com.doctorpatient.DocPatientProject.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/doctor")
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping("/create/{userId}")
    public DoctorResponseDto createDoctor(@Valid @RequestBody DoctorRequestDto doctorRequestDto, @PathVariable Long userId) {
        return doctorService.createDoctor(doctorRequestDto, userId);
    }

    @GetMapping("/{id}")
    public DoctorResponseDto getDoctorById(@PathVariable Long id) {
        return doctorService.getDoctorById(id);
    }

    @GetMapping("/getAll")
    public List<DoctorResponseDto> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    @PutMapping("/update/{id}")
    public DoctorResponseDto updateDoctor(@PathVariable Long id,@Valid @RequestBody DoctorRequestDto doctorRequestDto) {
        return doctorService.updateDoctor(id, doctorRequestDto);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return "Doctor deleted successfully with id: " + id + "!!!";
    }

}