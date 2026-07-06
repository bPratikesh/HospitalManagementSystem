package com.doctorpatient.DocPatientProject.controller;

import com.doctorpatient.DocPatientProject.dto.AppointmentRequestDto;
import com.doctorpatient.DocPatientProject.dto.AppointmentResponseDto;
import com.doctorpatient.DocPatientProject.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointment")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping("/patient/{patientId}/doctor/{doctorId}")
    public AppointmentResponseDto createAppointment(@PathVariable Long patientId, @PathVariable Long doctorId,@Valid @RequestBody AppointmentRequestDto appointmentRequestDto){
        return appointmentService.createAppointment(patientId, doctorId, appointmentRequestDto);
    }

    @GetMapping("/{id}")
    public AppointmentResponseDto getAppointment(@PathVariable long id){
        return appointmentService.getAppointmentById(id);
    }

    @GetMapping("/getAll")
    public List<AppointmentResponseDto> getAllAppointment(){
        return appointmentService.getAllAppointments();
    }

    @PutMapping("/patient/cancel/{appointmentId}")
    public AppointmentResponseDto cancelAppointmentByPatient(@PathVariable Long appointmentId) {
        return appointmentService.cancelAppointmentByPatient(appointmentId);
    }

    @PutMapping("/doctor/cancel/{appointmentId}")
    public AppointmentResponseDto cancelAppointmentByDoctor(@PathVariable Long appointmentId) {
        return appointmentService.cancelAppointmentByDoctor(appointmentId);
    }

    @GetMapping("/patient/{patientId}")
    public List<AppointmentResponseDto> getAppointmentsByPatient(@PathVariable Long patientId) {
        return appointmentService.getAppointmentsByPatient(patientId);
    }

    @GetMapping("/doctor/{doctorId}")
    public List<AppointmentResponseDto> getAppointmentsByDoctor(
            @PathVariable Long doctorId) {

        return appointmentService.getAppointmentsByDoctor(doctorId);
    }
}