package com.example.ft2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ft2.model.MLocation;

public interface MLocationRepository extends JpaRepository<MLocation, Long>{
    // List<MLocation> 
}
