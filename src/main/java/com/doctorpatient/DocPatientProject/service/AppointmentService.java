package com.doctorpatient.DocPatientProject.service;

import com.doctorpatient.DocPatientProject.entity.Appointment;
import com.doctorpatient.DocPatientProject.entity.Doctor;
import com.doctorpatient.DocPatientProject.entity.Patient;
import com.doctorpatient.DocPatientProject.repository.AppointmentRepo;
import com.doctorpatient.DocPatientProject.repository.DoctorRepo;
import com.doctorpatient.DocPatientProject.repository.PatientRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final DoctorRepo doctorRepo;
    private final PatientRepo patientRepo;
    private final AppointmentRepo appointmentRepo;

    public Appointment createAppointment(Long patientId, Long doctorId, Appointment appointment){
        Doctor doctor = doctorRepo.findById(doctorId).orElseThrow(()->
                new RuntimeException("Doctor not found with id: "+doctorId));
        Patient patient = patientRepo.findById(patientId).orElseThrow(()->
                new RuntimeException("Patient not found with id: "+patientId));

        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        return appointmentRepo.save(appointment);
    }

    public Appointment getAppointmentById(Long  id){
        return appointmentRepo.findById(id).orElseThrow(()->
                new RuntimeException("Appointment not found with id: "+ id));
    }

    public List<Appointment> getAllAppointments(){
        return appointmentRepo.findAll();
    }

    public void deleteAppointment(Long id){
        Appointment appointment = getAppointmentById(id);
        appointmentRepo.delete(appointment);
    }


}
