package com.doctorpatient.DocPatientProject.service;

import com.doctorpatient.DocPatientProject.dto.PrescriptionRequestDto;
import com.doctorpatient.DocPatientProject.dto.PrescriptionResponseDto;
import com.doctorpatient.DocPatientProject.entity.Appointment;
import com.doctorpatient.DocPatientProject.entity.Prescription;
import com.doctorpatient.DocPatientProject.entity.enums.AppointmentStatus;
import com.doctorpatient.DocPatientProject.exception.BadRequestException;
import com.doctorpatient.DocPatientProject.exception.ResourceNotFoundException;
import com.doctorpatient.DocPatientProject.repository.AppointmentRepo;
import com.doctorpatient.DocPatientProject.repository.PrescriptionRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrescriptionService {

    private final PrescriptionRepo prescriptionRepo;
    private final AppointmentRepo appointmentRepo;
    private final ModelMapper modelMapper;

    @Transactional
    public PrescriptionResponseDto createPrescription(Long appointmentId, PrescriptionRequestDto prescriptionRequestDto){

        Appointment appointment = appointmentRepo.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found"));

        if(appointment.getStatus() == AppointmentStatus.CANCELLED){
            throw new BadRequestException("Cannot prescribe for cancelled appointment.");
        }

        if(appointment.getStatus() == AppointmentStatus.COMPLETED){
            throw new BadRequestException("Prescription already created.");
        }

        Prescription prescription = modelMapper.map(prescriptionRequestDto, Prescription.class);
        prescription.setAppointment(appointment);
        prescription.setPrescriptionDate(LocalDate.now());

        Prescription savedPrescription = prescriptionRepo.save(prescription);
        appointment.setStatus(AppointmentStatus.COMPLETED);
        appointmentRepo.save(appointment);
        return modelMapper.map(savedPrescription, PrescriptionResponseDto.class);
    }

    public List<PrescriptionResponseDto> getAllPrescriptionsByPatient(Long patientId) {

        return prescriptionRepo.findByAppointmentPatientId(patientId)
                .stream()
                .map(prescription -> modelMapper.map(prescription, PrescriptionResponseDto.class))
                .collect(Collectors.toList());
    }

}