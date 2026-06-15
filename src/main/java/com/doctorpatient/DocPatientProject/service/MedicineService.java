package com.doctorpatient.DocPatientProject.service;

import com.doctorpatient.DocPatientProject.entity.Medicine;
import com.doctorpatient.DocPatientProject.repository.MedicineRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MedicineService {

    private final MedicineRepo medicineRepo;

    public Medicine createMedicine(Medicine medicine) {
        return medicineRepo.save(medicine);
    }
}