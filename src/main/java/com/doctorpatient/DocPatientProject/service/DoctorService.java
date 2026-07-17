package com.doctorpatient.DocPatientProject.service;

import com.doctorpatient.DocPatientProject.dto.DoctorCardResponseDto;
import com.doctorpatient.DocPatientProject.dto.DoctorRequestDto;
import com.doctorpatient.DocPatientProject.dto.DoctorResponseDto;
import com.doctorpatient.DocPatientProject.entity.Doctor;
import com.doctorpatient.DocPatientProject.entity.User;
import com.doctorpatient.DocPatientProject.exception.ResourceNotFoundException;
import com.doctorpatient.DocPatientProject.repository.DoctorRepo;
import com.doctorpatient.DocPatientProject.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepo doctorRepo;
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;

    public DoctorResponseDto createDoctor(DoctorRequestDto doctorRequestDto, Long userId) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        Doctor doctor = modelMapper.map(doctorRequestDto, Doctor.class);
        doctor.setUser(user);
        doctor.setWalletBalance(0.0);
        Doctor savedDoctor = doctorRepo.save(doctor);
        return modelMapper.map(savedDoctor, DoctorResponseDto.class);
    }

    public DoctorResponseDto getDoctorById(Long id) {

        Doctor doctor = doctorRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + id));
        return modelMapper.map(doctor, DoctorResponseDto.class);
    }

    public List<DoctorResponseDto> getAllDoctors() {

        return doctorRepo.findAll()
                .stream()
                .map(doctor -> modelMapper.map(doctor, DoctorResponseDto.class))
                .collect(Collectors.toList());
    }

    public DoctorResponseDto updateDoctor(Long id, DoctorRequestDto doctorRequestDto) {

        Doctor existingDoctor = doctorRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + id));

        if (doctorRequestDto.getDoctorName() != null)
            existingDoctor.setDoctorName(doctorRequestDto.getDoctorName());

        if (doctorRequestDto.getExperience() != null)
            existingDoctor.setExperience(doctorRequestDto.getExperience());

        if (doctorRequestDto.getSpeciality() != null)
            existingDoctor.setSpeciality(doctorRequestDto.getSpeciality());

        if (doctorRequestDto.getConsultationFee() != null)
            existingDoctor.setConsultationFee(doctorRequestDto.getConsultationFee());

        Doctor updatedDoctor = doctorRepo.save(existingDoctor);
        return modelMapper.map(updatedDoctor, DoctorResponseDto.class);
    }

    public void deleteDoctor(Long id) {

        Doctor doctor = doctorRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + id));
        doctorRepo.delete(doctor);
    }

    public List<DoctorCardResponseDto> getDoctorsForPatients() {
        List<Doctor> doctors = doctorRepo.findAll();
        return doctors.stream()
                .map(doctor -> modelMapper.map(doctor, DoctorCardResponseDto.class))
                .toList();
    }
    public List<DoctorResponseDto> searchDoctors(String name, String speciality) {

        if (name != null && name.isBlank()) {
            name = null;
        }
        if (speciality != null && speciality.isBlank()) {
            speciality = null;
        }
        return doctorRepo.searchDoctors(name, speciality)
                .stream()
                .map(doctor -> modelMapper.map(doctor, DoctorResponseDto.class))
                .collect(Collectors.toList());
    }

}