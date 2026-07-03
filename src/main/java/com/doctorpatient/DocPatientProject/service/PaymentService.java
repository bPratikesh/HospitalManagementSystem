package com.doctorpatient.DocPatientProject.service;

import com.doctorpatient.DocPatientProject.dto.AppointmentResponseDto;
import com.doctorpatient.DocPatientProject.entity.Appointment;
import com.doctorpatient.DocPatientProject.entity.Doctor;
import com.doctorpatient.DocPatientProject.entity.Patient;
import com.doctorpatient.DocPatientProject.entity.enums.AppointmentStatus;
import com.doctorpatient.DocPatientProject.entity.enums.PaymentStatus;
import com.doctorpatient.DocPatientProject.exception.BadRequestException;
import com.doctorpatient.DocPatientProject.exception.PaymentException;
import com.doctorpatient.DocPatientProject.exception.ResourceNotFoundException;
import com.doctorpatient.DocPatientProject.repository.AppointmentRepo;
import com.doctorpatient.DocPatientProject.repository.DoctorRepo;
import com.doctorpatient.DocPatientProject.repository.PatientRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final AppointmentRepo appointmentRepo;
    private final PatientRepo patientRepo;
    private final DoctorRepo doctorRepo;
    private final ModelMapper modelMapper;

    @Transactional
    public AppointmentResponseDto payForAppointment(Long appointmentId){

        Appointment appointment = appointmentRepo.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found."));

        // Doctor must prescribe the medicine first
        if(appointment.getStatus() != AppointmentStatus.COMPLETED){
            throw new BadRequestException("Prescription is not available yet.");
        }

        if(appointment.getPaymentStatus() == PaymentStatus.PAID){
            throw new BadRequestException("Payment already completed.");
        }

        Patient patient = appointment.getPatient();
        Doctor doctor = appointment.getDoctor();

        Double consultationFee = doctor.getConsultationFee();

        // checking Wallet balance
        if(patient.getWalletBalance() < consultationFee){
            throw new PaymentException("Insufficient wallet balance.");
        }

        patient.setWalletBalance(
                patient.getWalletBalance() - consultationFee
        );

        doctor.setWalletBalance(
                doctor.getWalletBalance() + consultationFee
        );

        appointment.setPaymentStatus(PaymentStatus.PAID);

        patientRepo.save(patient);
        doctorRepo.save(doctor);
        Appointment savedAppointment = appointmentRepo.save(appointment);

        return modelMapper.map(savedAppointment, AppointmentResponseDto.class);
    }

}