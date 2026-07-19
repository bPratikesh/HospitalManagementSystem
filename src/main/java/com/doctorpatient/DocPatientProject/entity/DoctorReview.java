package com.doctorpatient.DocPatientProject.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class DoctorReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer rating;

    @Column(length = 1000)
    private String review;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    @JsonIgnore
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    @JsonIgnore
    private Patient patient;

    @OneToOne
    @JoinColumn(name = "appointment_id", unique = true)
    @JsonIgnore
    private Appointment appointment;
}
