package com.example.ft2.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.ft2.model.MBlood;

public interface MBloodRepository extends JpaRepository<MBlood,Long>{
    // List<Mblood>

    List<MBlood> findByIsDelete(Boolean isDelete);

    @Query(value = "select * from m_blood where code=?1 and is_delete = true", nativeQuery = true)
    Optional<MBlood> findByCode(String code);


}
