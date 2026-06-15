package com.doctorpatient.DocPatientProject.repository;

import com.doctorpatient.DocPatientProject.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineRepo extends JpaRepository<Medicine, Long> {
}
