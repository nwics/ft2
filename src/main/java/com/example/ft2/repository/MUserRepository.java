package com.example.ft2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ft2.model.MUser;

public interface MUserRepository extends JpaRepository <MUser,Long>{
    MUser findByUsername(String username);
    
}
