package com.doctorpatient.DocPatientProject.service;

import com.doctorpatient.DocPatientProject.entity.Doctor;
import com.doctorpatient.DocPatientProject.entity.User;
import com.doctorpatient.DocPatientProject.repository.DoctorRepo;
import com.doctorpatient.DocPatientProject.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {
    private final DoctorRepo doctorRepo;
    private final UserRepo userRepo;

    public Doctor createDoctor(Doctor doctor, Long userId) {
        User user = userRepo.findById(userId).orElseThrow(()-> new RuntimeException("User not found with id: "+userId));
        doctor.setUser(user);
        return doctorRepo.save(doctor);
    }

    public Doctor getDoctorById(Long id) {
        return doctorRepo.findById(id).orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepo.findAll();
    }

    public Doctor updateDoctor(Long id, Doctor doctor) {

        Doctor existingDoctor = doctorRepo.findById(id).orElseThrow(() ->
                        new RuntimeException("Doctor not found with id: " + id));

        if (doctor.getDoctorName() != null)
            existingDoctor.setDoctorName(doctor.getDoctorName());
        if (doctor.getExperience() != null)
            existingDoctor.setExperience(doctor.getExperience());
        if (doctor.getSpeciality() != null)
            existingDoctor.setSpeciality(doctor.getSpeciality());
        if (doctor.getConsultationFee() != null)
            existingDoctor.setConsultationFee(doctor.getConsultationFee());
        return doctorRepo.save(existingDoctor);
    }

    public void deleteDoctor(Long id) {
        Doctor doctor = getDoctorById(id);
        doctorRepo.delete(doctor);
    }
}
