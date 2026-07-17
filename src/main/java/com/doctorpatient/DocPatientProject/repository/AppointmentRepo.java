package com.doctorpatient.DocPatientProject.repository;

import com.doctorpatient.DocPatientProject.entity.Appointment;
import com.doctorpatient.DocPatientProject.entity.enums.AppointmentSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatientId(Long Id);
    List<Appointment> findByDoctorId(Long doctorId);
    long countByDoctorIdAndAppointmentDateAndAppointmentSlot(Long doctorId,LocalDate appointmentDate, AppointmentSlot appointmentSlot);
    List<Appointment> findByAppointmentDate(LocalDate appointmentDate);
}
