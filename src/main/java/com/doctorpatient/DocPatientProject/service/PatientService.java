package com.doctorpatient.DocPatientProject.service;

import com.doctorpatient.DocPatientProject.dto.PatientRequestDto;
import com.doctorpatient.DocPatientProject.dto.PatientResponseDto;
import com.doctorpatient.DocPatientProject.entity.Patient;
import com.doctorpatient.DocPatientProject.entity.User;
import com.doctorpatient.DocPatientProject.repository.PatientRepo;
import com.doctorpatient.DocPatientProject.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepo patientRepo;
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;

    public PatientResponseDto createPatient(PatientRequestDto patientRequestDto, Long userId) {

        User user = userRepo.findById(userId).orElseThrow(() ->
                new RuntimeException("User not found with id: " + userId));

        Patient patient = modelMapper.map(patientRequestDto, Patient.class);
        patient.setUser(user);
        Patient savedPatient = patientRepo.save(patient);
        return modelMapper.map(savedPatient, PatientResponseDto.class);
    }

    public PatientResponseDto getPatientById(Long id) {

        Patient patient = patientRepo.findById(id).orElseThrow(() ->
                new RuntimeException("Patient not found with id: " + id));
        return modelMapper.map(patient, PatientResponseDto.class);
    }

    public List<PatientResponseDto> getAllPatients() {

        return patientRepo.findAll()
                .stream()
                .map(patient -> modelMapper.map(patient, PatientResponseDto.class))
                .collect(Collectors.toList());
    }

    public PatientResponseDto updatePatient(Long id, PatientRequestDto patientRequestDto) {

        Patient existingPatient = patientRepo.findById(id).orElseThrow(() ->
                new RuntimeException("Patient not found with id: " + id));
        if (patientRequestDto.getPatientName() != null)
            existingPatient.setPatientName(patientRequestDto.getPatientName());

        if (patientRequestDto.getAge() != null)
            existingPatient.setAge(patientRequestDto.getAge());

        if (patientRequestDto.getGender() != null)
            existingPatient.setGender(patientRequestDto.getGender());

        if (patientRequestDto.getBloodGroup() != null)
            existingPatient.setBloodGroup(patientRequestDto.getBloodGroup());
        Patient updatedPatient = patientRepo.save(existingPatient);
        return modelMapper.map(updatedPatient, PatientResponseDto.class);
    }

    public void deletePatient(Long id) {

        Patient patient = patientRepo.findById(id).orElseThrow(() ->
                new RuntimeException("Patient not found with id: " + id));
        patientRepo.delete(patient);
    }
}