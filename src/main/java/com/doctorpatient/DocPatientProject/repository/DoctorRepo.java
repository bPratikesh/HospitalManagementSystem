package com.doctorpatient.DocPatientProject.repository;

import com.doctorpatient.DocPatientProject.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepo extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByUserId(Long userId);
    @Query("""
                SELECT d FROM Doctor d
                WHERE
                (:name IS NULL OR LOWER(d.doctorName) LIKE LOWER(CONCAT('%', :name, '%')))
                AND
                (:speciality IS NULL OR LOWER(d.speciality) = LOWER(:speciality))
            """)
    List<Doctor> searchDoctors(
            @Param("name") String name,
            @Param("speciality") String speciality
    );
}
