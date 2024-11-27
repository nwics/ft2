package com.example.ft2.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.ft2.model.BloodGroup;
import com.example.ft2.model.MBlood;

public interface MBloodRepository extends JpaRepository<MBlood,Long>{
    // List<Mblood>

    List<MBlood> findByIsDelete(Boolean isDelete);

    @Query(value = "select * from m_blood where code=?1 and is_delete = true", nativeQuery = true)
    Optional<MBlood> findByCode(String code);

    
    // @Query(nativeQuery = true, value = "SELECT * FROM m_blood_group mbg where mbg.is_delete = false ORDER BY id")
    // List<BloodGroup> order();

    // @Query(value = "SELECT CASE WHEN COUNT(mbg)>0 THEN TRUE ELSE FALSE END FROM m_blood_group mbg WHERE UPPER(mbg.code)=UPPER(?1)", nativeQuery=true)
    // Boolean existsByCode(String code);

    // @Query(value = "SELECT CASE WHEN COUNT(mbg)>0 THEN TRUE ELSE FALSE END FROM m_blood_group mbg WHERE UPPER(mbg.code) = UPPER(?1) AND mbg.id != ?2", nativeQuery=true)
    // Boolean existsByCode(String code, Long id);

    // @Query(nativeQuery = true, value = "SELECT * FROM m_blood_group mbg WHERE code ilike %?1% OR description ilike %?1% ORDER BY id")
    // List<BloodGroup> searching(String search);

}
