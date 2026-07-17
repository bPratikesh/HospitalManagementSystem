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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepo userRepo;
    private final DoctorRepo doctorRepo;
    private final PatientRepo patientRepo;
    private final ModelMapper modelMapper;

    private static final int MAX_FAILED_ATTEMPTS = 3;
    private static final int LOCK_DURATION_HOURS = 24;

    public DoctorResponseDto registerDoctor(RegisterDoctorRequestDto dto) {

        if (userRepo.findByEmail(dto.getEmail()).isPresent()) {
            throw new BadRequestException("Email already exists.");
        }

        User user = modelMapper.map(dto, User.class);
        user.setName(dto.getDoctorName());
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
        user.setName(dto.getPatientName());
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
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Email or Password."));

        // Checking if account is locked or not
        if (Boolean.TRUE.equals(user.getAccountLocked())) {
            LocalDateTime unlockTime = user.getLockTime().plusHours(LOCK_DURATION_HOURS);

            if (LocalDateTime.now().isBefore(unlockTime)) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a");

                throw new BadRequestException("Account is locked due to multiple failed login attempts.Try again after: "
                                + unlockTime.format(formatter)
                );
            } else {
                // Automatically unlocking the profilee after 24 hours
                user.setAccountLocked(false);
                user.setFailedAttempts(0);
                user.setLockTime(null);
                userRepo.save(user);
            }
        }

        // Wrong password scenario
        if (!user.getPassword().equals(dto.getPassword())) {
            int attempts = user.getFailedAttempts() + 1;
            user.setFailedAttempts(attempts);
            if (attempts >= MAX_FAILED_ATTEMPTS) {
                user.setAccountLocked(true);
                user.setLockTime(LocalDateTime.now());
                userRepo.save(user);
                throw new BadRequestException("Account locked for 24 hours due to 3 failed login attempts.");
            } else {
                userRepo.save(user);
                throw new BadRequestException("Invalid password. Attempts remaining: " + (MAX_FAILED_ATTEMPTS - attempts));
            }
        }

        // Successful login scenario
        user.setFailedAttempts(0);
        user.setAccountLocked(false);
        user.setLockTime(null);
        userRepo.save(user);

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