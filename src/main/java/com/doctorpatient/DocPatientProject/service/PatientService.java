package com.doctorpatient.DocPatientProject.service;

import com.doctorpatient.DocPatientProject.entity.Patient;
import com.doctorpatient.DocPatientProject.entity.User;
import com.doctorpatient.DocPatientProject.repository.PatientRepo;
import com.doctorpatient.DocPatientProject.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {
    private final PatientRepo patientRepo;
    private final UserRepo userRepo;

    public Patient createPatient(Patient patient, Long userId) {

        User user = userRepo.findById(userId).orElseThrow(() ->
                        new RuntimeException("User not found with id: " + userId));
        patient.setUser(user);
        return patientRepo.save(patient);
    }

    public Patient getPatientById(Long id) {
        return patientRepo.findById(id).orElseThrow(() ->
                        new RuntimeException("Patient not found with id: " + id));
    }

    public List<Patient> getAllPatients() {
        return patientRepo.findAll();
    }

    public Patient updatePatient(Long id, Patient patient) {

        Patient existingPatient = patientRepo.findById(id).orElseThrow(() ->
                        new RuntimeException("Patient not found with id: " + id));

        if (patient.getPatientName() != null)
            existingPatient.setPatientName(patient.getPatientName());
        if (patient.getAge() != null)
            existingPatient.setAge(patient.getAge());
        if (patient.getGender() != null)
            existingPatient.setGender(patient.getGender());
        if (patient.getBloodGroup() != null)
            existingPatient.setBloodGroup(patient.getBloodGroup());

        return patientRepo.save(existingPatient);
    }

    public void deletePatient(Long id) {
        Patient patient = getPatientById(id);
        patientRepo.delete(patient);
    }
}
