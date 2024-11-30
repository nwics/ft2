package com.example.ft2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.ft2.model.TCurrentDoctorSpecialization;

public interface TCurrentDoctorSpecializationRepository  extends JpaRepository<TCurrentDoctorSpecialization, Long>{
	@Query(value = "select * from t_current_doctor_specialization where is_delete = false and doctor_id = ?1", nativeQuery = true)
	TCurrentDoctorSpecialization findByDoctorId(Long doctorId);
}
