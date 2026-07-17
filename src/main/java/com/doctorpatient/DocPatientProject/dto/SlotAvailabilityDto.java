package com.doctorpatient.DocPatientProject.dto;

import com.doctorpatient.DocPatientProject.entity.enums.AppointmentSlot;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SlotAvailabilityDto {

    private AppointmentSlot slot;

    private int booked;

    private int remaining;

    private boolean available;
}