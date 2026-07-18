package com.doctorpatient.DocPatientProject.service;

import com.doctorpatient.DocPatientProject.dto.AppointmentRequestDto;
import com.doctorpatient.DocPatientProject.dto.AppointmentResponseDto;
import com.doctorpatient.DocPatientProject.dto.SlotAvailabilityDto;
import com.doctorpatient.DocPatientProject.entity.Appointment;
import com.doctorpatient.DocPatientProject.entity.Doctor;
import com.doctorpatient.DocPatientProject.entity.Patient;
import com.doctorpatient.DocPatientProject.entity.enums.AppointmentSlot;
import com.doctorpatient.DocPatientProject.entity.enums.AppointmentStatus;
import com.doctorpatient.DocPatientProject.entity.enums.PaymentStatus;
import com.doctorpatient.DocPatientProject.exception.BadRequestException;
import com.doctorpatient.DocPatientProject.exception.ResourceNotFoundException;
import com.doctorpatient.DocPatientProject.repository.AppointmentRepo;
import com.doctorpatient.DocPatientProject.repository.DoctorRepo;
import com.doctorpatient.DocPatientProject.repository.PatientRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final DoctorRepo doctorRepo;
    private final PatientRepo patientRepo;
    private final AppointmentRepo appointmentRepo;
    private final ModelMapper modelMapper;

    public AppointmentResponseDto createAppointment(Long patientId,  Long doctorId, AppointmentRequestDto appointmentRequestDto) {

        Doctor doctor = doctorRepo.findById(doctorId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Doctor not found with id: " + doctorId));

        Patient patient = patientRepo.findById(patientId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Patient not found with id: " + patientId));

        long bookedPatients =
                appointmentRepo.countByDoctorIdAndAppointmentDateAndAppointmentSlot(
                        doctorId,
                        appointmentRequestDto.getAppointmentDate(),
                        appointmentRequestDto.getAppointmentSlot()
                );

        if (bookedPatients >= 8) {
            throw new BadRequestException(
                    "Selected slot is full. Please choose another slot."
            );
        }

        Appointment appointment = modelMapper.map(appointmentRequestDto, Appointment.class);

        appointment.setDoctor(doctor);
        appointment.setPatient(patient);

        appointment.setStatus(AppointmentStatus.BOOKED);
        appointment.setPaymentStatus(PaymentStatus.PENDING);

        Appointment savedAppointment = appointmentRepo.save(appointment);

        return modelMapper.map(savedAppointment, AppointmentResponseDto.class);
    }

    public AppointmentResponseDto getAppointmentById(Long id){

        Appointment appointment = appointmentRepo.findById(id).orElseThrow(()->
                new ResourceNotFoundException("Appointment not found with id: "+ id));
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
            throw new BadRequestException("Appointment is already cancelled.");
        }
        appointment.setStatus(AppointmentStatus.CANCELLED);
        return appointmentRepo.save(appointment);
    }

    public List<AppointmentResponseDto> getAppointmentsByPatient(Long patientId) {

        Patient patient = patientRepo.findById(patientId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Patient not found with id: " + patientId));
        return appointmentRepo.findByPatientIdOrderByAppointmentDateDescAppointmentSlotDesc(patient.getId())
                .stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentResponseDto.class))
                .collect(Collectors.toList());
    }

    public List<AppointmentResponseDto> getAppointmentsByDoctor(Long doctorId) {

        Doctor doctor = doctorRepo.findById(doctorId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Doctor not found with id: " + doctorId));

        return appointmentRepo.findByDoctorIdOrderByAppointmentDateDescAppointmentSlotDesc(doctor.getId())
                .stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentResponseDto.class))
                .collect(Collectors.toList());
    }
    public List<SlotAvailabilityDto> getSlotAvailability(Long doctorId, LocalDate appointmentDate){
        List<SlotAvailabilityDto> slotAvailability = new ArrayList<>();
        for (AppointmentSlot slot : AppointmentSlot.values()) {
            long booked =
                    appointmentRepo.countByDoctorIdAndAppointmentDateAndAppointmentSlot(doctorId, appointmentDate, slot);

            slotAvailability.add(
                    new SlotAvailabilityDto(
                            slot,
                            (int) booked,
                            8 - (int) booked,
                            booked < 8
                    )
            );
        }

        return slotAvailability;
    }

}