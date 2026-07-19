package com.doctorpatient.DocPatientProject.entity;

import com.doctorpatient.DocPatientProject.entity.enums.AppointmentSlot;
import com.doctorpatient.DocPatientProject.entity.enums.AppointmentStatus;
import com.doctorpatient.DocPatientProject.entity.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String symptoms;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate appointmentDate;

    @Enumerated(EnumType.STRING)
    private AppointmentSlot appointmentSlot;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @OneToOne(mappedBy = "appointment", cascade = CascadeType.ALL)
    @JsonIgnore
    private Prescription prescription;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    @OneToOne(mappedBy = "appointment", cascade = CascadeType.ALL)
    @JsonIgnore
    private DoctorReview review;
}
