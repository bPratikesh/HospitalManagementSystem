package com.doctorpatient.DocPatientProject.controller;

import com.doctorpatient.DocPatientProject.entity.Appointment;
import com.doctorpatient.DocPatientProject.repository.AppointmentRepo;
import com.doctorpatient.DocPatientProject.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/appointment")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService appointmentService;

    @PostMapping("/patient/{patientId}/doctor/{doctorId}")
    public Appointment createAppointment(@PathVariable Long patientId, @PathVariable Long doctorId, @RequestBody Appointment appointment){
        return appointmentService.createAppointment(patientId, doctorId, appointment);
    }

    @GetMapping("/{id}")
    public Appointment getAppointment(@PathVariable long id){
        return appointmentService.getAppointmentById(id);
    }

    @GetMapping("/getAll")
    public List<Appointment> getAllAppointment(){
        return appointmentService.getAllAppointments();
    }

    @DeleteMapping("/delete/{id}")
    public String deleteAppointment(@PathVariable Long id){
        appointmentService.deleteAppointment(id);
        return "Appointment deleted Successfully!!!";
    }
}
