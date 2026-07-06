package com.doctorpatient.DocPatientProject.repository;

import com.doctorpatient.DocPatientProject.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatientId(Long Id);
    List<Appointment> findByDoctorId(Long doctorId);
}
