package com.example.ft2.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.jaxb.SpringDataJaxb.PageRequestDto;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ft2.model.MDoctor;
import com.example.ft2.repository.MDoctorRepository;

@RestController
// @CrossOrigin("*")
@RequestMapping("/doctor/")
public class ApiMDoctorController {
    // @AutowiredMDoctorRepository mDoctorRepository;
    @Autowired MDoctorRepository mDoctorRepository;

	// Response-----------------------------------------------------------------------------------------------------------
	private Map<String, Object> response(String status, String message, Object data, Object schedule) {

		List<String> keyData = new ArrayList<>();
		keyData.add("priceStartFrom");
		keyData.add("priceUntilFrom");
		keyData.add("name");
		keyData.add("fullAddress");

		List<Object> doctorDetailResponse = new ArrayList<>();
		List<Object> doctorDetail = (List<Object>) data;
		for (int i = 0; i < doctorDetail.size(); i++) {
			Map<String, Object> doctorDetailMap = new HashMap<>();
			Object[] doctorDetailObj = (Object[]) doctorDetail.get(i);
			for (int j = 0; j < doctorDetailObj.length; j++) {
				doctorDetailMap.put(keyData.get(j), doctorDetailObj[j]);
			}
			doctorDetailResponse.add(doctorDetailMap);
		}

		List<String> keySchedule = new ArrayList<>();
		keySchedule.add("day");
		keySchedule.add("timeScheduleStart");
		keySchedule.add("timeScheduleEnd");

		List<Object> scheduleResponse = new ArrayList<>();
		List<Object> scheduleList = (List<Object>) schedule;
		for (int i = 0; i < scheduleList.size(); i++) {
			Map<String, Object> scheduleMap = new HashMap<>();
			Object[] scheduleObj = (Object[]) scheduleList.get(i);
			for (int j = 0; j < scheduleObj.length; j++) {
				scheduleMap.put(keySchedule.get(j), scheduleObj[j]);
			}
			scheduleResponse.add(scheduleMap);
		}

		Map<String, Object> response = new HashMap<>();
		response.put("status", status);
		response.put("message", message);
		response.put("data", doctorDetailResponse);
		response.put("schedule", scheduleResponse);

		return response;
	}

	// Doctor
	// detail-----------------------------------------------------------------------------------------------------------
	@GetMapping("detail/{id}")
	public ResponseEntity<Map<String, Object>> getDetailDoctorById(@PathVariable("id") Long id) {
		try {
			List<Object> doctorDetail = this.mDoctorRepository.findDoctorDetail(id);
			List<Object> schedule = this.mDoctorRepository.listSchedule(id);
			if (doctorDetail.isEmpty()) {
				return new ResponseEntity<>(response("success", "No Data", doctorDetail, schedule), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(response("success", "Success Fetch Data", doctorDetail, schedule),
						HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(response("failed", "Failed Fetch Data", new ArrayList<>(), new ArrayList<>()),
					HttpStatus.OK);
		}
	}
	// -----------------------------------------------------------------------------------------------------------

	@GetMapping("show")
	public ResponseEntity<List<MDoctor>> findAllDoctor() {
		try {
			List<MDoctor> listDoctor = this.mDoctorRepository.findByIsDelete();
			return new ResponseEntity<>(listDoctor, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

	}

	@GetMapping("{id}")
	public ResponseEntity<Object> getDoctorById(@PathVariable("id") Long id) {
		try {
			MDoctor doctor = this.mDoctorRepository.findByDoctorId(id);
			return new ResponseEntity<Object>(doctor, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}

    @GetMapping("pagingsearch")
	public ResponseEntity<Map<String, Object>> getAllDoctorBySearchWithLocation(
			@RequestParam(required = false) String namaDokter, @RequestParam(required = true) Long idSpesialis,
			@RequestParam(required = false) String namaTindakan, @RequestParam(required = false) Long lokasiId,
			@RequestParam(defaultValue = "4") Long perPage, @RequestParam(defaultValue = "0") Long offset,
			@RequestParam(defaultValue = "0") Long page) {
		try {
			offset = perPage * page;
			List<Object> allData = this.mDoctorRepository.showBySearchWithLokasiAll(namaDokter, idSpesialis,
					namaTindakan, lokasiId);
			List<Object> pages = this.mDoctorRepository.findBySearchWithLokasi(namaDokter, idSpesialis, namaTindakan,
					lokasiId, perPage, offset);

			List<Map<String, Object>> dokterResponse = new ArrayList<>();

			for (Object dokterObj : pages) {
				Map<String, Object> dokterMap = new HashMap<>();
				Object[] dokterArray = (Object[]) dokterObj;

				dokterMap.put("dokterId", dokterArray[0]);
				dokterMap.put("fullName", dokterArray[1]);
				dokterMap.put("namaSpesialis", dokterArray[2]);
				dokterMap.put("namaTindakan", dokterArray[3]);
				dokterMap.put("namaMedicalFacility", dokterArray[4]);
				dokterMap.put("namaKecamatan", dokterArray[5]);
				dokterMap.put("parentId", dokterArray[6]);
				dokterMap.put("pengalaman", dokterArray[7]);
				dokterMap.put("jenisRumahSakit", dokterArray[8]);
				dokterMap.put("fotoUrl", dokterArray[9]);
				dokterMap.put("hariBuka", dokterArray[10]);
				dokterMap.put("lokasiId", dokterArray[11]);

				dokterResponse.add(dokterMap);
			}

			int total = (int) Math.ceil((float)allData.size() / (float)perPage);
			if (total == 0) {
				total = 1;
			}

			Map<String, Object> response = new HashMap<>();
			response.put("data", dokterResponse);
			response.put("total", allData.size());
			response.put("totalPages", total);
			response.put("page", page);
			response.put("perPage", perPage);

			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			Map<String, Object> err = new HashMap<>();
			err.put("error", e.getMessage());
			return new ResponseEntity<>(err,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
