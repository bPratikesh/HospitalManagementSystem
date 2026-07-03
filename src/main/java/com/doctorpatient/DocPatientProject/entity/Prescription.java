package com.doctorpatient.DocPatientProject.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "prescriptions")
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prescriptionId;

    private String diagnosis;

    @Column(length = 3000)
    private String prescribedMedicines;

    @Column(length = 1000)
    private String instructions;

    private LocalDate prescriptionDate;

    @OneToOne
    @JoinColumn(name = "appointment_id", nullable = false)
    private Appointment appointment;
}