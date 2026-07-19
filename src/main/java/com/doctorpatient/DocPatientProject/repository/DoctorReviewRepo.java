package com.doctorpatient.DocPatientProject.repository;

import org.springframework.stereotype.Repository;
import com.doctorpatient.DocPatientProject.entity.DoctorReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorReviewRepo extends JpaRepository<DoctorReview, Long> {

    List<DoctorReview> findByDoctorIdOrderByCreatedAtDesc(Long doctorId);
    Optional<DoctorReview> findByAppointmentId(Long appointmentId);
    boolean existsByAppointmentId(Long appointmentId);
    Long countByDoctorId(Long doctorId);
    @Query("""
            SELECT AVG(r.rating)
            FROM DoctorReview r
            WHERE r.doctor.id = :doctorId
            """)
    Double findAverageRatingByDoctorId(Long doctorId);

}
