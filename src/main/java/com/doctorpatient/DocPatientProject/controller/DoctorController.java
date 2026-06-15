package com.doctorpatient.DocPatientProject.controller;

import com.doctorpatient.DocPatientProject.entity.Doctor;
import com.doctorpatient.DocPatientProject.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/doctor")
public class DoctorController {
    private final DoctorService doctorService;

    @PostMapping("/create/{userId}")
    public Doctor createDoctor(@RequestBody Doctor doctor, @PathVariable Long userId) {
        return doctorService.createDoctor(doctor, userId);
    }

    @GetMapping("/{id}")
    public Doctor getDoctorById(@PathVariable Long id) {
        return doctorService.getDoctorById(id);
    }

    @GetMapping("/getAll")
    public List<Doctor> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    @PutMapping("/update/{id}")
    public Doctor updateDoctor(@PathVariable Long id, @RequestBody Doctor doctor) {
        return doctorService.updateDoctor(id, doctor);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return "Doctor deleted successfully with id: "+id+"!!!";
    }
}
