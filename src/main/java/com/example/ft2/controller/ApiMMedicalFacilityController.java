package com.example.ft2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ft2.model.MMedicalFacility;
import com.example.ft2.repository.MMedicalFacilityRepository;

@RestController
@RequestMapping("/facility/")
public class ApiMMedicalFacilityController {
@Autowired
	public MMedicalFacilityRepository mMedicalFacilityRepository;
	
	@GetMapping("show")
	public ResponseEntity<List<MMedicalFacility>> getAllMedicalFacility() {
		try {
			List<MMedicalFacility> listFacility = this.mMedicalFacilityRepository.findByIsDelete();
			return new ResponseEntity<>(listFacility, HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
}
