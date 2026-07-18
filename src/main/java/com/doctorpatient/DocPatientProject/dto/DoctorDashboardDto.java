package com.doctorpatient.DocPatientProject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDashboardDto {

    private Long totalAppointments;
    private Long bookedAppointments;
    private Long completedAppointments;
    private Long cancelledAppointments;
    private Long totalPatients;
    private Double walletBalance;
}