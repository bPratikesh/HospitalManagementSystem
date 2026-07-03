package com.doctorpatient.DocPatientProject.controller;

import com.doctorpatient.DocPatientProject.dto.AppointmentResponseDto;
import com.doctorpatient.DocPatientProject.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PutMapping("/appointment/{appointmentId}")
    public AppointmentResponseDto payForAppointment(@PathVariable Long appointmentId){
        return paymentService.payForAppointment(appointmentId);
    }

}
