package com.doctorpatient.DocPatientProject.service;

import org.springframework.stereotype.Service;
import com.doctorpatient.DocPatientProject.dto.*;
import com.doctorpatient.DocPatientProject.entity.Doctor;
import com.doctorpatient.DocPatientProject.entity.Patient;
import com.doctorpatient.DocPatientProject.entity.User;
import com.doctorpatient.DocPatientProject.entity.enums.Role;
import com.doctorpatient.DocPatientProject.exception.BadRequestException;
import com.doctorpatient.DocPatientProject.exception.ResourceNotFoundException;
import com.doctorpatient.DocPatientProject.repository.DoctorRepo;
import com.doctorpatient.DocPatientProject.repository.PatientRepo;
import com.doctorpatient.DocPatientProject.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepo userRepo;
    private final DoctorRepo doctorRepo;
    private final PatientRepo patientRepo;
    private final ModelMapper modelMapper;

    public DoctorResponseDto registerDoctor(RegisterDoctorRequestDto dto) {

        if (userRepo.findByEmail(dto.getEmail()).isPresent()) {
            throw new BadRequestException("Email already exists.");
        }

        User user = modelMapper.map(dto, User.class);
        user.setRole(Role.DOCTOR);

        User savedUser = userRepo.save(user);
        Doctor doctor = modelMapper.map(dto, Doctor.class);
        doctor.setUser(savedUser);
        doctor.setWalletBalance(0.0);

        Doctor savedDoctor = doctorRepo.save(doctor);
        return modelMapper.map(savedDoctor, DoctorResponseDto.class);
    }

    public PatientResponseDto registerPatient(RegisterPatientRequestDto dto) {

        if (userRepo.findByEmail(dto.getEmail()).isPresent()) {
            throw new BadRequestException("Email already exists.");
        }

        User user = modelMapper.map(dto, User.class);
        user.setRole(Role.PATIENT);

        User savedUser = userRepo.save(user);
        Patient patient = modelMapper.map(dto, Patient.class);
        patient.setUser(savedUser);
        patient.setWalletBalance(5000.0);

        Patient savedPatient = patientRepo.save(patient);
        return modelMapper.map(savedPatient, PatientResponseDto.class);
    }

    public LoginResponseDto login(LoginRequestDto dto) {

        User user = userRepo.findByEmail(dto.getEmail())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Invalid Email or Password."));

        if (!user.getPassword().equals(dto.getPassword())) {
            throw new BadRequestException("Invalid Email or Password.");
        }

        LoginResponseDto response = new LoginResponseDto();

        response.setUserId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());

        if (user.getRole() == Role.DOCTOR) {

            Doctor doctor = doctorRepo.findByUserId(user.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Doctor not found."));
            response.setDoctorId(doctor.getId());
        } else {

            Patient patient = patientRepo.findByUserId(user.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Patient not found."));
            response.setPatientId(patient.getId());
        }
        return response;
    }
}