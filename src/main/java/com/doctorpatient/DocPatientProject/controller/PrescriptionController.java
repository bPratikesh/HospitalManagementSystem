package com.doctorpatient.DocPatientProject.controller;

import com.doctorpatient.DocPatientProject.dto.PrescriptionRequestDto;
import com.doctorpatient.DocPatientProject.dto.PrescriptionResponseDto;
import com.doctorpatient.DocPatientProject.service.PrescriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prescription")
@RequiredArgsConstructor
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    @PostMapping("/appointment/{appointmentId}")
    public PrescriptionResponseDto createPrescription(@PathVariable Long appointmentId,@Valid @RequestBody PrescriptionRequestDto prescriptionRequestDto) {
        return prescriptionService.createPrescription(appointmentId, prescriptionRequestDto);
    }

    @GetMapping("/patient/{patientId}")
    public List<PrescriptionResponseDto> getAllPrescriptionsByPatient(@PathVariable Long patientId) {
        return prescriptionService.getAllPrescriptionsByPatient(patientId);
    }
    @GetMapping("/appointment/{appointmentId}")
    public PrescriptionResponseDto getPrescriptionByAppointment(
            @PathVariable Long appointmentId) {

        return prescriptionService.getPrescriptionByAppointment(appointmentId);
    }

}