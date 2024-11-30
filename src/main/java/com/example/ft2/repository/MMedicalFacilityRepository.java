package com.example.ft2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.ft2.model.MMedicalFacility;

public interface MMedicalFacilityRepository extends JpaRepository<MMedicalFacility,Long>{

    @Query(value = "select * from m_medical_facility where is_delete = false", nativeQuery = true)
	List<MMedicalFacility> findByIsDelete();
}
