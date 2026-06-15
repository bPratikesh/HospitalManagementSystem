package com.doctorpatient.DocPatientProject.controller;

import com.doctorpatient.DocPatientProject.entity.Patient;
import com.doctorpatient.DocPatientProject.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/patient")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;
    @PostMapping("/create/{userId}")
    public Patient createPatient(@RequestBody Patient patient, @PathVariable Long userId) {
        return patientService.createPatient(patient, userId);
    }

    @GetMapping("/{id}")
    public Patient getPatientById(@PathVariable Long id) {
        return patientService.getPatientById(id);
    }

    @GetMapping("/getAll")
    public List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }

    @PutMapping("/update/{id}")
    public Patient updatePatient(@PathVariable Long id, @RequestBody Patient patient) {
        return patientService.updatePatient(id, patient);
    }

    @DeleteMapping("/delete/{id}")
    public String deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return "Patient deleted successfully";
    }
}
