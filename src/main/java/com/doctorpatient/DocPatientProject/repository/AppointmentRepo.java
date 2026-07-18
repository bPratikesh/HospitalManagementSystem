package com.doctorpatient.DocPatientProject.repository;

import com.doctorpatient.DocPatientProject.entity.Appointment;
import com.doctorpatient.DocPatientProject.entity.enums.AppointmentSlot;
import com.doctorpatient.DocPatientProject.entity.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment, Long> {

    // Patient Appointments (Latest First)
    List<Appointment> findByPatientIdOrderByAppointmentDateDescAppointmentSlotDesc(Long patientId);

    // Doctor Appointments (Latest First)
    List<Appointment> findByDoctorIdOrderByAppointmentDateDescAppointmentSlotDesc(Long doctorId);

    long countByDoctorIdAndAppointmentDateAndAppointmentSlot(
            Long doctorId,
            LocalDate appointmentDate,
            AppointmentSlot appointmentSlot
    );

    List<Appointment> findByAppointmentDate(LocalDate appointmentDate);

    Long countByDoctorId(Long doctorId);

    Long countByDoctorIdAndStatus(Long doctorId, AppointmentStatus status);

    @Query("""
            SELECT COUNT(DISTINCT a.patient.id)
            FROM Appointment a
            WHERE a.doctor.id = :doctorId
            """)
    Long countDistinctPatients(Long doctorId);
}