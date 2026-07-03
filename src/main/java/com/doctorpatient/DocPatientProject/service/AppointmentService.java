package com.doctorpatient.DocPatientProject.service;

import com.doctorpatient.DocPatientProject.dto.AppointmentRequestDto;
import com.doctorpatient.DocPatientProject.dto.AppointmentResponseDto;
import com.doctorpatient.DocPatientProject.entity.Appointment;
import com.doctorpatient.DocPatientProject.entity.Doctor;
import com.doctorpatient.DocPatientProject.entity.Patient;
import com.doctorpatient.DocPatientProject.entity.enums.AppointmentStatus;
import com.doctorpatient.DocPatientProject.repository.AppointmentRepo;
import com.doctorpatient.DocPatientProject.repository.DoctorRepo;
import com.doctorpatient.DocPatientProject.repository.PatientRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final DoctorRepo doctorRepo;
    private final PatientRepo patientRepo;
    private final AppointmentRepo appointmentRepo;
    private final ModelMapper modelMapper;

    public AppointmentResponseDto createAppointment(Long patientId, Long doctorId, AppointmentRequestDto appointmentRequestDto){

        Doctor doctor = doctorRepo.findById(doctorId).orElseThrow(()->
                new RuntimeException("Doctor not found with id: "+doctorId));

        Patient patient = patientRepo.findById(patientId).orElseThrow(()->
                new RuntimeException("Patient not found with id: "+patientId));

        Appointment appointment = modelMapper.map(appointmentRequestDto, Appointment.class);
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setStatus(AppointmentStatus.BOOKED);
        Appointment savedAppointment = appointmentRepo.save(appointment);
        return modelMapper.map(savedAppointment, AppointmentResponseDto.class);
    }

    public AppointmentResponseDto getAppointmentById(Long id){

        Appointment appointment = appointmentRepo.findById(id).orElseThrow(()->
                new RuntimeException("Appointment not found with id: "+ id));
        return modelMapper.map(appointment, AppointmentResponseDto.class);
    }

    public List<AppointmentResponseDto> getAllAppointments(){

        return appointmentRepo.findAll()
                .stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentResponseDto.class))
                .collect(Collectors.toList());
    }

    public AppointmentResponseDto cancelAppointmentByPatient(Long appointmentId) {
        return modelMapper.map(cancelAppointment(appointmentId), AppointmentResponseDto.class);
    }

    public AppointmentResponseDto cancelAppointmentByDoctor(Long appointmentId) {
        return modelMapper.map(cancelAppointment(appointmentId), AppointmentResponseDto.class);
    }

    private Appointment cancelAppointment(Long appointmentId) {

        Appointment appointment = appointmentRepo.findById(appointmentId).orElseThrow(()->
                new RuntimeException("Appointment not found with id: "+ appointmentId));

        if (appointment.getStatus() == AppointmentStatus.COMPLETED) {
            throw new RuntimeException("Completed appointment cannot be cancelled.");
        }
        if (appointment.getStatus() == AppointmentStatus.CANCELLED) {
            throw new RuntimeException("Appointment is already cancelled.");
        }
        appointment.setStatus(AppointmentStatus.CANCELLED);
        return appointmentRepo.save(appointment);
    }

    public List<AppointmentResponseDto> getAppointmentsByPatient(Long patientId) {

        Patient patient = patientRepo.findById(patientId)
                .orElseThrow(() ->
                        new RuntimeException("Patient not found with id: " + patientId));
        return appointmentRepo.findByPatientId(patient.getId())
                .stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentResponseDto.class))
                .collect(Collectors.toList());
    }

}