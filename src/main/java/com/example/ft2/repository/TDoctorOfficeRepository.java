package com.example.ft2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.ft2.model.TDoctorOffice;

public interface TDoctorOfficeRepository extends JpaRepository<TDoctorOffice,Long>{
    @Query(value = "select * from t_doctor_office where is_delete = false", nativeQuery = true)
	List<TDoctorOffice> findByIsDelete();
	
	@Query(value = "select * from t_doctor_office tdo where is_delete = false and doctor_id = ?1 order by start_date asc", nativeQuery = true)
	List<TDoctorOffice> findByDoctorId(Long id);
}
