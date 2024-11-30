package com.example.ft2.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ft2.model.TDoctorTreatment;
import com.example.ft2.repository.TDoctorTreatmentRepository;

@RestController
@RequestMapping("/treatment/")
public class ApiTDoctorTreatmentController {

    @Autowired TDoctorTreatmentRepository repository;
    @GetMapping("{id}")
	public ResponseEntity<List<TDoctorTreatment>> getByDoctorId(@PathVariable("id") Long id) {
		try {
			List<TDoctorTreatment> listData = this.repository.findByDoctorId(id);
			return new ResponseEntity<List<TDoctorTreatment>>(listData, HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<List<TDoctorTreatment>>(HttpStatus.NO_CONTENT);
		}
	}
	
	@GetMapping("tindakan")
	public ResponseEntity<List<TDoctorTreatment>> getAll() {
		try {
			List<TDoctorTreatment> listData = this.repository.findAll();
			return new ResponseEntity<List<TDoctorTreatment>>(listData, HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<List<TDoctorTreatment>>(HttpStatus.NO_CONTENT);
		}
	}
	
	@PostMapping("addtreatment")
	public ResponseEntity<Object> addTreatment (@RequestBody TDoctorTreatment treatment) {
		try {
			List<TDoctorTreatment> dataPresent = this.repository.findByName(treatment.getName(), treatment.getDoctorId());
			if(!dataPresent.isEmpty()) {
				return new ResponseEntity<>("Data Sudah Ada", HttpStatus.OK);
			}
		}catch(Exception e) {
			
		}
		treatment.setCreatedBy(treatment.getDoctorId());
		treatment.setIsDelete(false);
		treatment.setCreatedOn(new Date());
		
		TDoctorTreatment tindakan = this.repository.save(treatment);
		if(tindakan.equals(treatment)) {
			return new ResponseEntity<Object>("Save Success", HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>("Save Failed", HttpStatus.BAD_REQUEST);
		}
	}
	@GetMapping("get/{id}")
	public ResponseEntity<List<TDoctorTreatment>> getTindakanById(@PathVariable("id") Long id) {
		try {
			Optional<TDoctorTreatment> tindakan = this.repository.findById(id);
			if (tindakan.isPresent()) {
				ResponseEntity rest = new ResponseEntity<>(tindakan, HttpStatus.OK);
				return rest;

			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			return new ResponseEntity<List<TDoctorTreatment>>(HttpStatus.NO_CONTENT);
		}
	}
	@PutMapping("deletetreatment/{id}")
	public ResponseEntity<Object> deleteTreatment (@PathVariable ("id") long id) {
		Optional<TDoctorTreatment> dataTreatment = this.repository.findById(id);
		
		if(dataTreatment.isPresent()) {
		TDoctorTreatment treatment = dataTreatment.get();
		treatment.setDeletedBy(treatment.getDoctorId());
		treatment.setDeletedOn(new Date());
		treatment.setIsDelete(true);
		this.repository.save(treatment);
		return new ResponseEntity<Object> ("Delete Success",HttpStatus.CREATED);
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("all")
	public ResponseEntity<List<Object>> getAllByIsDelete() {
		try {
			List<Object> listData = this.repository.showAllTreatmentByIsDelete();
			return new ResponseEntity<>(listData, HttpStatus.OK);
		} catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
}
