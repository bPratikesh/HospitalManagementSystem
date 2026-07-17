package com.doctorpatient.DocPatientProject.repository;

import com.doctorpatient.DocPatientProject.entity.Appointment;
import com.doctorpatient.DocPatientProject.entity.Prescription;
import com.doctorpatient.DocPatientProject.entity.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PrescriptionRepo extends JpaRepository<Prescription, Long> {
    List<Prescription> findByAppointmentPatientIdAndAppointmentPaymentStatus(Long patientId, PaymentStatus paymentStatus);
    Optional<Prescription> findByAppointmentId(Long appointmentId);
}
