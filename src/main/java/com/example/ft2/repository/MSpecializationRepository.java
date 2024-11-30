package com.example.ft2.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.ft2.model.MSpecialization;

// import com.miniproject335b.app.model.MPaymentMethod;
// import com.miniproject335b.app.model.Mspecialization;

public interface MSpecializationRepository extends JpaRepository<MSpecialization,Long> {

    @Query(value="select * from m_specialization where is_delete = false", nativeQuery = true)
	List<MSpecialization> findByIsDelete();
	
	@Query(value="select * from m_specialization where name ilike concat('%',?1,'%') and is_delete = false", nativeQuery = true)
	List<MSpecialization> findByName(String name);
	
	Page<MSpecialization> findByIsDelete(Pageable page, Boolean isDelete);
	
	// @Query(value = "SELECT * FROM m_payment_method WHERE is_delete=false", nativeQuery = true)
    // Page<MPaymentMethod> getMPaymentMethodByPageable(Pageable pageable);
	
	@Query(value="select * from m_specialization where name ilike concat('%',?1,'%') and is_delete = false",nativeQuery = true)
	Page<MSpecialization> findByNamePageable(String name,Pageable page);
}
