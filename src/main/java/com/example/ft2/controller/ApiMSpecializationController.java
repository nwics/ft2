package com.example.ft2.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ft2.model.MSpecialization;
import com.example.ft2.repository.MSpecializationRepository;


@RestController
@RequestMapping("/dokter/")
public class ApiMSpecializationController {
    @Autowired
	MSpecializationRepository specializationRepository;

	@GetMapping("spesialis")
	public ResponseEntity<List<MSpecialization>> getAllSpesialis() {
		try {
			List<MSpecialization> listSpesialis = this.specializationRepository.findByIsDelete();
			return new ResponseEntity<>(listSpesialis, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping("spesialis/{id}")
	public ResponseEntity<List<MSpecialization>> getSpesialisById(@PathVariable("id") Long id) {
		try {
			Optional<MSpecialization> spesialisasi = this.specializationRepository.findById(id);
			if (spesialisasi.isPresent()) {
				ResponseEntity rest = new ResponseEntity<>(spesialisasi, HttpStatus.OK);
				return rest;

			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			return new ResponseEntity<List<MSpecialization>>(HttpStatus.NO_CONTENT);
		}
	}

	@PostMapping("spesialis/add")
	public ResponseEntity<Object> saveSpesialis(@RequestBody MSpecialization specialization){
		try {
			List<MSpecialization> dataPresent = this.specializationRepository.findByName(specialization.getName());
			if(!dataPresent.isEmpty()) {
				return new ResponseEntity<>("Data Sudah Ada", HttpStatus.OK);
			}
		}catch(Exception e) {
		}
		MSpecialization dataSpesialis = this.specializationRepository.save(specialization);
		if(dataSpesialis.equals(specialization)) {
			return new ResponseEntity<>("Save Data Success", HttpStatus.CREATED);
		}else {
			return new ResponseEntity<>("Save Failed", HttpStatus.BAD_REQUEST);
		}
		
	}

	@PutMapping("spesialis/delete")
	public ResponseEntity<Object> deleteSpesialis(@RequestBody MSpecialization spesialis) {
		Long id = spesialis.getId();
		Optional<MSpecialization> spesialisData = this.specializationRepository.findById(id);

		if (spesialisData.isPresent()) {
			spesialis.setCreateBy(spesialisData.get().getCreateBy());
			spesialis.setCreateOn(spesialisData.get().getCreateOn());
			this.specializationRepository.save(spesialis);
			return new ResponseEntity<Object>("Delete Success", HttpStatus.CREATED);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("spesialis/edit")
	public ResponseEntity<Object> editSpesialis(@RequestBody MSpecialization spesialis) {
		try {
			
			List<MSpecialization> dataPresent = this.specializationRepository.findByName(spesialis.getName());
			if(!dataPresent.isEmpty()) {
				return new ResponseEntity<>("Data Sudah Ada", HttpStatus.OK);
			}
			Long id = spesialis.getId();
			Optional<MSpecialization> spesialisData = this.specializationRepository.findById(id);

			if (spesialisData.isPresent()) {
				spesialis.setModifyOn(new Date());
				spesialis.setCreateBy(spesialisData.get().getCreateBy());
				spesialis.setCreateOn(spesialisData.get().getCreateOn());
				this.specializationRepository.save(spesialis);
				return new ResponseEntity<Object>("Update Data Success", HttpStatus.CREATED);

			} else {
				return ResponseEntity.notFound().build();
			}

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping("spesialis/search")
	public ResponseEntity<List<MSpecialization>> getSpesialisByName(@RequestParam("keyword") String keyword) {
		List<MSpecialization> listSpesialis = this.specializationRepository.findByName(keyword);
		return new ResponseEntity<>(listSpesialis, HttpStatus.OK);
	}

	@GetMapping("spesialis/paging")
	public ResponseEntity<Map<String, Object>> getAllSpesialisPages(@RequestParam(defaultValue = "5") int per_page,
    @RequestParam(defaultValue = "0") int page) {
		try {
			List<MSpecialization> spesialis = new ArrayList<>();
			Pageable pagingSort = PageRequest.of(page, per_page);
			Page<MSpecialization> pages;

			pages = this.specializationRepository.findByIsDelete(pagingSort, false);

			spesialis = pages.getContent();

			Map<String, Object> response = new HashMap<>();
			response.put("page", pages.getNumber());
			response.put("data", spesialis);
			response.put("perPages", per_page);
			response.put("total", pages.getTotalElements());
			response.put("total_pages", pages.getTotalPages());

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("spesialis/pagingSearch")
	public ResponseEntity<Map<String, Object>> getAllSpesialisPagesByName(@RequestParam("keyword") String keyword,
			@RequestParam(defaultValue = "5") int per_page, @RequestParam(defaultValue = "0") int page) {
		try {
			List<MSpecialization> spesialis = new ArrayList<>();
			Pageable pagingSort = PageRequest.of(page, per_page);
			Page<MSpecialization> pages;

			pages = this.specializationRepository.findByNamePageable(keyword, pagingSort);

			spesialis = pages.getContent();

			Map<String, Object> response = new HashMap<>();
			response.put("page", pages.getNumber());
			response.put("data", spesialis);
			response.put("perPages", per_page);
			response.put("value", keyword);
			response.put("total", pages.getTotalElements());
			response.put("total_pages", pages.getTotalPages());

			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
